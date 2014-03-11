package com.cathive.fx.cdi.demos;

import com.cathive.fx.cdi.WeldApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jboss.weld.Weld;

import javax.inject.Inject;

/**
 * This demo application demonstrates the CDI-based injection of custom made JavaFX-GUI components.
 * The three components are injected via <link>javax.inject.Inject</link> and defined via FXML files
 * linked to their class representations via the <link>com.cathive.fx.inject.core.FXMLComponent</link>
 * annotation.
 *
 * @author Benjamin P. Jung
 * @author Alexander Erben
 */
public class FxDemoApplication extends WeldApplication {

    @Inject
    private ContactDetailsPane contactDetailsPane;
    @Inject
    private HelloButton helloButton1;
    @Inject
    private HelloButton helloButton2;

    @Override
    public void start(Stage stage) throws Exception {
        final BorderPane rootPane = new BorderPane();
        final ToolBar toolBar = new ToolBar();

        final Label weldVersionLabel = new Label();
        weldVersionLabel.setText("JBoss WELD spec: " + Weld.class.getPackage().getSpecificationVersion());

        toolBar.getItems().addAll(helloButton1, helloButton2);
        rootPane.setTop(toolBar);
        rootPane.setCenter(contactDetailsPane);
        rootPane.setBottom(weldVersionLabel);
        stage.setScene(new Scene(rootPane));
        stage.setWidth(200);
        stage.setHeight(200);
        stage.show();
    }

    /**
     * Boot the app.
     * @param args ignored for now
     */
    public static void main(final String... args) {
        Application.launch(FxDemoApplication.class, args);
    }
}
