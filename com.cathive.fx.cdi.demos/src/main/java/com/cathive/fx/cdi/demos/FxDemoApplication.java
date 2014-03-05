package com.cathive.fx.cdi.demos;

import com.cathive.fx.cdi.WeldApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.inject.Inject;

/**
 * @author Benjamin P. Jung
 */
public class FxDemoApplication extends WeldApplication {

    @Inject private HelloButton helloButton1;
    @Inject private HelloButton helloButton2;

    @Override
    public void start(Stage stage) throws Exception {
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(helloButton1, helloButton2);
        stage.setScene(new Scene(vbox));
        stage.setWidth(200);
        stage.setHeight(200);
        stage.show();
    }

    public static void main(final String... args) {
        Application.launch(FxDemoApplication.class, args);
    }
}
