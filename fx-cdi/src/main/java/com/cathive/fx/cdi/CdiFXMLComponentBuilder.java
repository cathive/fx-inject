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

import com.cathive.fx.inject.core.FXMLComponentBuilder;

import javax.enterprise.inject.spi.CDI;

/**
 * Component builder for CDI
 *
 * @author Benjamin P. Jung
 */
public class CdiFXMLComponentBuilder<T> extends FXMLComponentBuilder<T> {

    public CdiFXMLComponentBuilder(Class<T> componentClass) {
        super(componentClass);
    }

    @Override
    protected <T> T getInstance(Class<T> clazz) {
        return CDI.current().select(clazz).get();
    }
}
