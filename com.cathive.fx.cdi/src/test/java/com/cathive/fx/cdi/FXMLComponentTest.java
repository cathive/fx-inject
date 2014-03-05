package com.cathive.fx.cdi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.testng.annotations.Test;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.io.IOException;
import java.util.function.Consumer;

import static javafx.application.Application.launch;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Benjamin P. Jung
 */
@Test
public class FXMLComponentTest {

    static Consumer<Stage> uiFinishedListener;

    @Test
    public void testInit() throws Exception {
        assertNull(FxCdiExtension.getJavaFxApplication());
        uiFinishedListener = (Stage uut) -> {
            uut.hide();
        };
        launch(MyTestApplication.class);
    }

    public static class MyTestApplication extends WeldApplication {

        @Inject
        private SimpleFXMLComponent simpleFXMLComponent;


        @Override
        public void start(final Stage stage) throws Exception {
            stage.show();
            uiFinishedListener.accept(stage);
        }
    }

}
