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
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;

import javax.enterprise.inject.spi.CDI;

/**
 * CDI-aware FXMLLoader
 * @author Benjamin P. Jung
 */
class CdiFXMLLoader extends FXMLLoader {

    // Some marker strings for unspecified values.
    public static final String LOCATION_UNSPECIFIED = "$$$LOCATION_UNSPECIFIED$$$";
    public static final String RESOURCES_UNSPECIFIED = "$$$RESOURCES_UNSPECIFIED$$$";
    public static final String CHARSET_UNSPECIFIED = "$$$CHARSET_UNSPECIFIED$$$";

    /**
     * A reference to all the package names that won't work as supposed to when trying
     * to instantiate beans from within via CDI in conjunction with FXML builders.
     * <p>This will avoid an exception message caused by a {@link com.sun.javafx.fxml.PropertyNotFoundException}
     * <em>'Property "${propertyName}" does not exist or is read-only'</em>.</p>
     * @see com.sun.javafx.fxml.PropertyNotFoundException
     */
    private static final String[] CDI_BLACKLIST = new String[] {
            "javafx",
            "java.awt",
            "javax.swing"
    };

    /** A default (non-CDI aware) JavaFX builder factory. */
    private final JavaFXBuilderFactory defaultBuilderFactory;

    /**
     * Default (private) constructor.
     */
    private CdiFXMLLoader() {

        super();

        // Uses the currently loaded CDI implementation to look up controller classes
        // that have been specified via "fx:controller='...'" in our FXML files.
        this.setControllerFactory((aClass) -> CDI.current().select(aClass));

        // Initializes the default JavaFX builder factory to be used for non CDI-aware beans.
        this.defaultBuilderFactory = new JavaFXBuilderFactory();

        this.setBuilderFactory((aClass) -> {

            // Uses the default builder factory to retrieve builders where applicable.
            final Builder<?> defaultBuilder = defaultBuilderFactory.getBuilder(aClass);
            if (defaultBuilder != null) {
                return defaultBuilder;
            }

            // Make sure that we use a "null" builder for components that are 'blacklisted'.
            final String packageName = aClass.getPackage().getName();
            for (final String blacklisted : CDI_BLACKLIST) {
                if (packageName.startsWith(blacklisted)) {
                    return null;
                }
            }

            return new CdiFXMLComponentBuilder(aClass);

        });

    }

    @Override
    public String toString() {
        return String.format("[CDI-aware] %s", super.toString());
    }

}