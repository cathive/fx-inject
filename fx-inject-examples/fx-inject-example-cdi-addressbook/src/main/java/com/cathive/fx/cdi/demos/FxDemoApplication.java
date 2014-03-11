package com.cathive.fx.cdi.demos;

import com.cathive.fx.cdi.WeldApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jboss.weld.Weld;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * @author Benjamin P. Jung
 */
public class FxDemoApplication extends WeldApplication {

    private BorderPane rootPane;

    @Inject private ContactDetailsPane contactDetailsPane;

    @Inject private HelloButton helloButton1;
    @Inject private HelloButton helloButton2;

    @Override
    public void start(Stage stage) throws Exception {

        rootPane = new BorderPane();

        final ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(helloButton1, helloButton2);
        rootPane.setTop(toolBar);

        rootPane.setCenter(contactDetailsPane);

        final Label weldVersionLabel = new Label();

        final StringBuilder weldVersion = new StringBuilder("JBoss WELD spec: ");
        weldVersion.append(Weld.class.getPackage().getSpecificationVersion());

        weldVersionLabel.setText(weldVersion.toString());
        rootPane.setBottom(weldVersionLabel);

        stage.setScene(new Scene(rootPane));
        stage.setWidth(200);
        stage.setHeight(200);
        stage.show();
    }

    public static void main(final String... args) {
        Application.launch(FxDemoApplication.class, args);
    }
}
