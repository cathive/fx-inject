/*
 * Copyright (C) 2013,2014 The Cat Hive Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cathive.fx.inject.core;

import javafx.util.Builder;
import javafx.util.StringConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract base class for all component builders that use
 * dependency injection framework to construct instances.
 * <p>
 * Currently, the logic to find appropriate setter methods a and look up the
 * StringConverters in a Map seems a duplicate of what has already been implemented
 * in the JavaFX runtime.
 * A better solution may follow in the future.
 *
 * @author Benjamin P. Jung
 * @since 1.1.0
 */
public abstract class FXMLComponentBuilder<T> extends AbstractMap<String, Object> implements Builder<T> {

    private final Class<T> componentClass;

    private final Map<String, Object> componentProperties = new HashMap<>();

    protected FXMLComponentBuilder(final Class<T> componentClass) {
        super();
        this.componentClass = componentClass;
    }

    @Override
    public Object put(String key, Object value) {
        componentProperties.put(key, value);
        return null;
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T build() {
        final T component = getInstance(this.componentClass);
        for (String key : componentProperties.keySet()) {
            final Object value = componentProperties.get(key);
            final String setterName = String.format("set%s%s", key.substring(0, 1).toUpperCase(), key.substring(1));
            try {
                Method setterMethod = null;
                for (Method method : componentClass.getMethods()) {
                    if (method.getName().equals(setterName)) {
                        setterMethod = method;
                        break;
                    }
                }
                if (setterMethod == null) {
                    throw new IllegalStateException(String.format("No setter for field '%s' could be found.", key));
                }
                final Class<?> paramType = setterMethod.getParameterTypes()[0];
                Object paramValue;
                if (Enum.class.isAssignableFrom(paramType)) {
                    paramValue = paramType.getMethod("valueOf", String.class).invoke(setterMethod, value);
                } else {
                    final StringConverter<?> stringConverter = getStringConverter(paramType);
                    paramValue = stringConverter.fromString((String) value);
                }
                setterMethod.invoke(component, paramValue);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return component;
    }

    /**
     * Returns an instance of the requested class from the dependency injection framework being used.
     *
     * @param clazz Class to be constructed via DI container.
     * @param <T>   Type of the class to be constructed.
     * @return An instance of the requested class that has been fetched
     * via the concrete implementation of DI container being used.
     */
    protected abstract <T> T getInstance(Class<T> clazz);

    private StringConverter<?> getStringConverter(Class<?> valueClass) throws InstantiationException, IllegalAccessException {
        Class<? extends StringConverter<?>> aClass = StringConverterRetriever.retrieveConverterFor(valueClass);
        if (aClass == null)
            throw new IllegalArgumentException(String.format("Can't find StringConverter for class '%s'.", valueClass.getName()));
        return aClass.newInstance();
    }


}