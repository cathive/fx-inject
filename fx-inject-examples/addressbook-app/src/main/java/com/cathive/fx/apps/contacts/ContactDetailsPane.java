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
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    @Inject private ContactRendererFactory displayNameRenderers;

    @FXML private Label displayNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private ImageView photoImageView;

    // <editor-fold desc="Property: read only">
    public static final String READ_ONLY_PROPERTY = "readOnly";
    private final BooleanProperty readOnly = new SimpleBooleanProperty(this, READ_ONLY_PROPERTY);
    public boolean isReadOnly() {
        return this.readOnly.get();
    }
    public void setReadOnly(final boolean readOnly) {
        this.readOnly.set(readOnly);
    }
    public BooleanProperty readOnlyBooleanProperty() {
        return this.readOnly;
    }
    // </editor-fold>

    /** Contact to be displayed by this component (thus: the "model"). */
    private final ObjectProperty<Contact> contact = new SimpleObjectProperty<>(this, "contact");
    public void setContact(final Contact contact) { this.contact.set(contact); }
    public Contact getContact() { return this.contact.get(); }
    public ObjectProperty<Contact> contactProperty() { return this.contact; }

    public ContactDetailsPane() {
        super();
    }

    @PostConstruct
    protected void init() {

        this.contactProperty().addListener((observableValue, contact1, contact2) -> {
            this.displayNameLabel.textProperty().unbind();
            if (contact2 == null) {
                this.displayNameLabel.setText("");
                this.photoImageView.setImage(null);
            } else {
                this.displayNameLabel.textProperty().bind(this.displayNameRenderers.createDisplayNameBinding(contact2));
                this.photoImageView.imageProperty().bind(Bindings.createObjectBinding(() ->
                                contact2.getPhoto() != null ? contact2.getPhoto()
                                        : this.displayNameRenderers.getDefaultPhoto(contact2),
                        contact2.photoProperty()
                ));
            }
        });

    }

}
