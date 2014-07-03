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

package com.cathive.fx.cdi;

import javax.enterprise.inject.spi.CDI;
import javax.interceptor.AroundConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.cathive.fx.inject.core.FXMLComponent;

import javafx.fxml.FXMLLoader;

/**
 * This factory is responsible for the production of CDI-aware FXML-based custom components that
 * have been annotated as such using the {@link com.cathive.fx.inject.core.FXMLComponent @FXMLComponent} annotation.
 *
 * @author Benjamin P. Jung
 * @since 1.1.0
 */
@SuppressWarnings("UnusedDeclaration")
@FXMLComponent
@Interceptor
class FXMLComponentInterceptor {

    @AroundConstruct
    public void createCdiFXMLComponent(final InvocationContext invocationContext) throws Exception {

        // Performs c-tor invocation. Afterwards "invocationContext.getTarget()" will no longer return "null".
        invocationContext.proceed();

        // Fetches the newly created annotated object and it's class.
        final Object target = invocationContext.getTarget();
        final Class<?> targetClass = target.getClass();

        final FXMLComponent annotation = targetClass.getAnnotation(FXMLComponent.class);
        if (annotation == null) {
            throw new IllegalStateException(String.format("No @FXMLComponent annotation could be retrieved from class %s.", targetClass.getName()));
        }
        final FXMLLoader fxmlLoader = CDI.current().select(CdiFXMLLoader.class).get();
        CdiFXMLLoaderFactory.initializeFXMLLoader(
                fxmlLoader,
                targetClass,
                annotation.location(),
                annotation.resources(),
                annotation.charset());
        fxmlLoader.setRoot(target);
        fxmlLoader.setController(target);
        fxmlLoader.load();

    }

}
