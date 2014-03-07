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

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import static com.cathive.fx.cdi.CdiFXMLLoader.CHARSET_UNSPECIFIED;
import static com.cathive.fx.cdi.CdiFXMLLoader.LOCATION_UNSPECIFIED;
import static com.cathive.fx.cdi.CdiFXMLLoader.RESOURCES_UNSPECIFIED;

/**
 * This factory is responsible for the production of CDI-aware {@link javafx.fxml.FXMLLoader} instances.
 * @author Benjamin P. Jung
 * @since 1.0.0
 */
class FXMLLoaderFactory {

    @Produces
    @FXMLLoaderParams
    FXMLLoader createCdiFXMLLoader(@New final CdiFXMLLoader fxmlLoader, final InjectionPoint injectionPoint) {

        final Annotated annotated = injectionPoint.getAnnotated();
        final Class<?> declaringClass = injectionPoint.getMember().getDeclaringClass();

        // If an annotation of type @FXMLLoaderParams can be found, use it's parameters
        // to configure the FXMLLoader instance that shall be used to perform the loading
        // of the FXML file.
        if (annotated.isAnnotationPresent(FXMLLoaderParams.class)) {
            final FXMLLoaderParams annotation = annotated.getAnnotation(FXMLLoaderParams.class);
            initializeFXMLLoader(fxmlLoader, declaringClass, annotation.location(), annotation.resources(), annotation.charset());
        }

        return fxmlLoader;

    }

    /**
     * Initializes the given FXMLLoader using the provided parameters.
     * @param fxmlLoader
     * @param targetClass
     * @param location
     * @param resources
     * @param charset
     */
    static void initializeFXMLLoader(final FXMLLoader fxmlLoader, final Class<?> targetClass, final String location, final String resources, final String charset) {

        // Checks the location that has been specified (if any) and uses the default
        // class loader to create an URL that points to a FXML file on the classpath.
        if (location != null && !location.equals(LOCATION_UNSPECIFIED)) {
            final URL locationUrl = targetClass.getResource(location);
            if (locationUrl == null) {
                throw new IllegalArgumentException(String.format("Couldn't find FXML file: \"%s\".", location));
            }
            fxmlLoader.setLocation(locationUrl);
        }

        if (charset != null && !charset.equals(CHARSET_UNSPECIFIED)) {
            fxmlLoader.setCharset(Charset.forName(charset));
        }

        if (resources != null && !resources.equals(RESOURCES_UNSPECIFIED)) {
            fxmlLoader.setResources(ResourceBundle.getBundle(resources));
        }

    }

}
