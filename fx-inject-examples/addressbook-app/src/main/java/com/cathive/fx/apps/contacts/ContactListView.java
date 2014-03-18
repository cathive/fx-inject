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
import com.cathive.fx.apps.contacts.model.Person;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.enterprise.inject.spi.CDI;

/**
 * List view for contacts.
 * @author Benjamin P. Jung
 */
public class ContactListView extends ListView<Contact> {

    private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>(this, "selectedContact");
    public Contact getSelectedContact() { return this.selectedContact.get(); }
    public ReadOnlyObjectProperty<Contact> selectedContactProperty() { return this.selectedContact; }

    public ContactListView() {
        super();
        this.setCellFactory(listView -> {
            final ListCell<Contact> listCell = new ListCell<>();
            final ContactListCell graphics = CDI.current().select(ContactListCell.class).get();
            graphics.contactProperty().bind(listCell.itemProperty());
            listCell.setGraphic(graphics);
            return listCell;
        });

        this.selectedContact.bind(Bindings.createObjectBinding(() -> this.getSelectionModel().getSelectedItem(),
                this.selectionModelProperty(),
                this.getSelectionModel().selectedItemProperty()));

    }

}
