package com.cathive.fx.cdi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Test;

import javax.inject.Inject;
import java.net.URL;

/**
 * Very basic unit test that checks if the application can start up and close correctly
 * given a basic AnchorPane fxml on classpath
 *
 * @author Alexander Erben (a_erben@outlook.com)
 */
public class WeldApplicationTest extends WeldApplication {

    @Inject
    @FXMLLoaderParams()
    private FXMLLoader fxmlLoader;

    @Test
    public void testInit() throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = WeldApplicationTest.class.getResource("weldApplicationTest.fxml");
        AnchorPane pane = FXMLLoader.load(resource);
        stage.setScene(new Scene(pane));
        stage.show();
        stage.hide();
    }
}
