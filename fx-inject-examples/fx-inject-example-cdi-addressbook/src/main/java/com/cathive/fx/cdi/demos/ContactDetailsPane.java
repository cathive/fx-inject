package com.cathive.fx.cdi.demos;

import com.cathive.fx.inject.core.FXMLComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Simple contact details pane. Note how the FXML-file defining this component is linked via the
 * <link>com.cathive.fx.inject.core.FXMLComponent</link> annotation.
 * Neat, isn't it?
 *
 * @author Benjamin P. Jung
 * @author Alexander Erben
 */
@SuppressWarnings("UnusedDeclaration")
@FXMLComponent(location = "ContactDetailsPane.fxml")
public class ContactDetailsPane extends VBox {

    @FXML
    private HelloButton helloButton1;

}
