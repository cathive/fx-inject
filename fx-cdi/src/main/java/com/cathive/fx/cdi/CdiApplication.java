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

import com.cathive.fx.cdi.spi.CDILoader;
import javafx.application.Application;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * The purpose of this (abstract) class is to mark JavaFX applications that utilize CDI.
 *
 * <p>Make sure that you have an implementation of the {@link com.cathive.fx.cdi.spi.CDILoader}
 * on your classpath and it will be automatically picked up during initialization of the app.</p>
 * @author Benjamin P. Jung
 * @since 1.0.0
 */
public abstract class CdiApplication extends Application {

    private CDILoader fxCdiLoader;

    public CdiApplication() {

        super();

        // Sets the JavaFX application instance to be used when injecting an instance of this class.
        JavaFXExtension.setJavaFxApplication(this);

        // Searches for a FxCdiLoader instance.
        final ServiceLoader<CDILoader> serviceLoader = ServiceLoader.load(CDILoader.class);
        final Iterator<CDILoader> loaderIterator = serviceLoader.iterator();
        if (!loaderIterator.hasNext()) {
            throw new IllegalStateException("No CDI Loader implementation for JavaFX could be found on your classpath.");
        }
        final CDILoader loader = loaderIterator.next();
        if (loaderIterator.hasNext()) {
            throw new IllegalStateException("More than one CDI Loader implementation for JavaFX could be found on your classpath.");
        }
        this.fxCdiLoader = loader;

    }

    @Override
    public void init() throws Exception {
        super.init();
        this.fxCdiLoader.initialize();
    }

    @Override
    public void stop() throws Exception {
        this.fxCdiLoader.shutdown();
        JavaFXExtension.setJavaFxApplication(null);
        super.stop();
    }

    @Override
    public String toString() {
        return String.format("[CDI-aware] %s", super.toString());
    }

}
