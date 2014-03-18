/*
 * Copyright (C) 2013,2014 The Cat Hive Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cathive.fx.apps.contacts;

import com.cathive.fx.apps.contacts.model.Contact;
import com.cathive.fx.inject.core.FXMLComponent;
import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @FXML private Label displayNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;

    /** Contact to be displayed by this component (thus: the "model"). */
    private final ObjectProperty<Contact> contact = new SimpleObjectProperty<>(this, "contact");
    public void setContact(final Contact contact) { this.contact.set(contact); }
    public Contact getContact() { return this.contact.get(); }
    public ObjectProperty<Contact> contactProperty() { return this.contact; }

    public ContactDetailsPane() {

        super();

        this.contactProperty().addListener((observableValue, contact1, contact2) -> {
            if (contact2 != null) {
                this.displayNameLabel.textProperty().bind(contact2.displayNameProperty());
            }
            // TODO Handle null values.
        });
    }

    @PostConstruct
    public void init() {
        System.out.println(appInstance);
    }

}
