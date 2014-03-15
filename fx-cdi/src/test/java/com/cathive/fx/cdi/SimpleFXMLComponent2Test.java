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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Checks if a basic FXML component can be injected via CDI.
 *
 * @author Alexander Erben
 */
public class SimpleFXMLComponent2Test {

    @Test
    public void testInit() throws Exception {
        Application.launch(ComponentTestImpl.class);
    }

    public static class ComponentTestImpl extends CdiApplication {

        @Inject
        SimpleFXMLComponent component;

        @Override
        public void start(final Stage stage) throws Exception {
            assertThat(component, notNullValue());
            assertThat(component.isHello(), is(true));
            component.setText("Test");
            stage.setScene(new Scene(component));
            stage.show();

            SimpleFXMLComponent root = (SimpleFXMLComponent) stage.getScene().getRoot();
            assertThat(root.getText(), is("Test"));
            stage.hide();
        }
    }
}