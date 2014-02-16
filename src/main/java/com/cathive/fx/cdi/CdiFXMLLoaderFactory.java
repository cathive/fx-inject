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

import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionPoint;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

/**
 * @author Benjamin P. Jung
 * @since 1.0.0
 */
class CdiFXMLLoaderFactory {

    @Produces
    @FXMLLoaderParams
    public FXMLLoader createCdiFXMLLoader(final InjectionPoint injectionPoint) {

        final Annotated annotated = injectionPoint.getAnnotated();
        final Class<?> declaringClass = injectionPoint.getMember().getDeclaringClass();

        final FXMLLoader loader = new FXMLLoader() {
        };

        // Uses the currently loaded CDI implementation to look up controller classes
        // that have been specified via "fx:controller='...'" in our FXML files.
        loader.setControllerFactory((aClass) -> CDI.current().select(aClass));

        // If an annotation of type @FXMLLoaderParams can be found, use it's parameters
        // to configure the FXMLLoader instance that shall be used to perform the loading
        // of the FXML file.
        final FXMLLoaderParams fxmlLoaderParams = annotated.getAnnotation(FXMLLoaderParams.class);
        if (fxmlLoaderParams != null) {

            // Checks the location that has been specified (if any) and uses the default
            // class loader to create an URL that points to a FXML file on the classpath.
            final String location = fxmlLoaderParams.location();
            if (! location.equals(FXMLLoaderParams.LOCATION_UNSPECIFIED)) {
                loader.setLocation(declaringClass.getResource(location));
            }

            final String charset = fxmlLoaderParams.charset();
            if (! charset.equals(FXMLLoaderParams.CHARSET_UNSPECIFIED)) {
                loader.setCharset(Charset.forName(fxmlLoaderParams.charset()));
            }

            final String resources = fxmlLoaderParams.resources();
            if (!resources.equals(FXMLLoaderParams.RESOURCES_UNSPECIFIED)) {
                loader.setResources(ResourceBundle.getBundle(resources));
            }

        }

        return loader;

    }

}
