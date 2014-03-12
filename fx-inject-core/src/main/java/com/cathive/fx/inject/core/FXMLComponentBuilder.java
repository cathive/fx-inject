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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.util.Builder;
import javafx.util.StringConverter;
import javafx.util.converter.*;

/**
 * Abstract base class for all component builders that use some kind of
 * dependency injection framework to construct instances.
 * @author Benjamin P. Jung
 * @since 1.1.0
 */
public abstract class FXMLComponentBuilder<T> extends AbstractMap<String, Object> implements Builder<T> {

    // FIXME The whole logic that finds appropriate setter methods and look up the
    //       StringConverters in a Map seems a duplicate of what has already been implemented
    //       in the JavaFX runtime. Unfortunately I couldn't think of any better
    //       solution... :-(

    private static final Map<Class<?>, Class<? extends StringConverter<?>>> STRING_CONVERTERS;
    static {

        STRING_CONVERTERS = new HashMap<>();

        // String
        STRING_CONVERTERS.put(String.class, DefaultStringConverter.class);

        // Primitives
        STRING_CONVERTERS.put(boolean.class, BooleanStringConverter.class);
        STRING_CONVERTERS.put(byte.class, ByteStringConverter.class);
        STRING_CONVERTERS.put(char.class, CharacterStringConverter.class);
        STRING_CONVERTERS.put(double.class, DoubleStringConverter.class);
        STRING_CONVERTERS.put(float.class, FloatStringConverter.class);
        STRING_CONVERTERS.put(int.class, IntegerStringConverter.class);
        STRING_CONVERTERS.put(long.class, LongStringConverter.class);
        STRING_CONVERTERS.put(short.class, ShortStringConverter.class);

        // Primitive wrappers
        STRING_CONVERTERS.put(Boolean.class, BooleanStringConverter.class);
        STRING_CONVERTERS.put(Byte.class, ByteStringConverter.class);
        STRING_CONVERTERS.put(Character.class, CharacterStringConverter.class);
        STRING_CONVERTERS.put(Double.class, DoubleStringConverter.class);
        STRING_CONVERTERS.put(Float.class, FloatStringConverter.class);
        STRING_CONVERTERS.put(Integer.class, IntegerStringConverter.class);
        STRING_CONVERTERS.put(Long.class, LongStringConverter.class);
        STRING_CONVERTERS.put(Short.class, ShortStringConverter.class);

        // Other types
        STRING_CONVERTERS.put(BigDecimal.class, BigDecimalStringConverter.class);
        STRING_CONVERTERS.put(BigInteger.class, BigIntegerStringConverter.class);
    }

    private final Class<T> componentClass;

    private final Map<String, Object> componentProperties = new HashMap<>();

    protected FXMLComponentBuilder(final Class<T> componentClass) {
        super();
        this.componentClass = componentClass;
    }

    /**
     * Returns an instance of the requested class from the dependency injection framework being used.
     * @param clazz
     *         Class to be constructed via DI container.
     * @param <T>
     *         Type of the class to be constructed.
     * @return
     *         An instance of the requested class that has been fetched
     *         via the concrete implementation of DI container being used.
     */
    protected abstract <T> T getInstance(Class<T> clazz);

    @Override
    public T build() {
        final T component = getInstance(this.componentClass);
        for (String key: componentProperties.keySet()) {
            final Object value = componentProperties.get(key);
            final String setterName = String.format("set%s%s", key.substring(0, 1).toUpperCase(), key.substring(1));
            try {
                Method setterMethod = null;
                for (Method method: componentClass.getMethods()) {
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

    private StringConverter<?> getStringConverter(Class<?> valueClass) throws InstantiationException, IllegalAccessException {

        if (STRING_CONVERTERS.containsKey(valueClass)) {
            return STRING_CONVERTERS.get(valueClass).newInstance();
        } else {
            throw new IllegalArgumentException(String.format("Can't find StringConverter for class '%s'.", valueClass.getName()));
        }

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

}