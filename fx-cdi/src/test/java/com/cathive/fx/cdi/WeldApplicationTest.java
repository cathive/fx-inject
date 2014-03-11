package com.cathive.fx.cdi;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.function.Consumer;

import static com.cathive.fx.cdi.WeldApplicationTest.WeldAppTestImpl.uiFinishedListener;
import static javafx.application.Application.launch;
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
public class WeldApplicationTest
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
        launch(WeldAppTestImpl.class);
    }

    public static class WeldAppTestImpl extends WeldApplication
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