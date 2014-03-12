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

import com.cathive.fx.inject.core.FXMLLoaderParams;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.function.Consumer;

import static com.cathive.fx.cdi.WeldApplicationTest.WeldAppTestImpl.uiFinishedListener;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * Checks if the application can start up and close correctly,
 * given a basic AnchorPane fxml on classpath
 *
 * @author Alexander Erben
 * @author Benjamin P. Jung
 */
@Test
@PrepareForTest({FxCdiExtension.class, Application.class})
public class WeldApplicationTest extends PowerMockTestCase
{
    @Test
    public void testInit() throws Exception {
        assertNull(FxCdiExtension.getJavaFxApplication());
        uiFinishedListener = (Stage uut) -> {
            Button firstButton = (Button) uut.getScene().getRoot().getChildrenUnmodifiable().get(0);

            assertThat(firstButton.getText(), is("Weld App"));
            assertNotNull(FxCdiExtension.getJavaFxApplication());

            uut.hide();
        };
        Application.launch(WeldAppTestImpl.class);
    }

    public static class WeldAppTestImpl extends CdiApplication
    {
        static Consumer<Stage> uiFinishedListener;

        @Inject
        @FXMLLoaderParams(location = "weldApplicationTest.fxml")
        FXMLLoader fxmlLoader;

        @Override
        public void start(final Stage stage) throws Exception
        {
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            uiFinishedListener.accept(stage);
        }
    }
}