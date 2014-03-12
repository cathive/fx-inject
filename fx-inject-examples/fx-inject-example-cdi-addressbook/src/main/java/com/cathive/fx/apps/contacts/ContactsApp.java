package com.cathive.fx.apps.contacts;

import com.cathive.fx.cdi.CdiApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

/**
 * @author Benjamin P. Jung
 */
public class ContactsApp extends CdiApplication {

    @Inject
    private RootPane rootPane;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(this.rootPane));
        primaryStage.show();
    }

    public static void main(String... args) {
        Application.launch(ContactsApp.class, args);
    }

}
