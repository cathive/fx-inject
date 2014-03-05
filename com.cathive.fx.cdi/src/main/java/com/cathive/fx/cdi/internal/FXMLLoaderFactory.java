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

package com.cathive.fx.cdi.internal;

import com.cathive.fx.cdi.CdiFXMLLoader;
import com.cathive.fx.cdi.FXMLLoaderParams;
import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * This factory is responsible for the production of CDI-aware {@link javafx.fxml.FXMLLoader} instances.
 * @author Benjamin P. Jung
 * @since 1.0.0
 */
class FXMLLoaderFactory {

    @Produces
    @FXMLLoaderParams
    FXMLLoader createCdiFXMLLoader(final InjectionPoint injectionPoint) {

        final Annotated annotated = injectionPoint.getAnnotated();
        final Class<?> declaringClass = injectionPoint.getMember().getDeclaringClass();

        // If an annotation of type @FXMLLoaderParams can be found, use it's parameters
        // to configure the FXMLLoader instance that shall be used to perform the loading
        // of the FXML file.
        final FXMLLoaderParams fxmlLoaderParams = annotated.getAnnotation(FXMLLoaderParams.class);
        return CdiFXMLLoader.create(
                fxmlLoaderParams.location(),
                fxmlLoaderParams.resources(),
                fxmlLoaderParams.charset(),
                declaringClass);

    }

}
