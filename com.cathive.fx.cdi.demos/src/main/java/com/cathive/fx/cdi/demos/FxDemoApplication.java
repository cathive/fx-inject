package com.cathive.fx.cdi.demos;

import com.cathive.fx.cdi.WeldApplication;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.inject.Inject;

/**
 * @author Benjamin P. Jung
 */
public class FxDemoApplication extends WeldApplication {

    @Inject
    private MyLabel1 label1;

    @Override
    public void start(Stage stage) throws Exception {

    }

    public static void main(final String... args) {
        Application.launch(FxDemoApplication.class, args);
    }
}
