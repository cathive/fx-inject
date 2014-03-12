package com.cathive.fx.apps.contacts;

import com.cathive.fx.inject.core.FXMLComponent;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

/**
 * @author Benjamin P. Jung
 */
@FXMLComponent(location = "RootPane.fxml", resources = "com.cathive.fx.apps.contacts.Messages")
public class RootPane extends AnchorPane {

    @FXML
    private ToolBar toolBar;

}
