package com.cathive.fx.apps.contacts;

import com.cathive.fx.inject.core.FXMLComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * @author Benjamin P. Jung
 */
@FXMLComponent(location = "ContactDetailsPane.fxml", resources = "com.cathive.fx.apps.contacts.Messages")
public class ContactDetailsPane extends VBox {

    @Inject private Instance<ContactsApp> appInstance;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;

    public ContactDetailsPane() {
        super();
        System.out.println(this);
    }

    @PostConstruct
    public void init() {
        System.out.println(appInstance);
    }

}
