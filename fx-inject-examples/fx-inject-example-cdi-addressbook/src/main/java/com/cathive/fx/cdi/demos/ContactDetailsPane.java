package com.cathive.fx.cdi.demos;

import com.cathive.fx.cdi.FXMLComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * @author Benjamin P. Jung
 */
@FXMLComponent(location = "ContactDetailsPane.fxml")
public class ContactDetailsPane extends VBox {

    @FXML
    private HelloButton helloButton1;

}
