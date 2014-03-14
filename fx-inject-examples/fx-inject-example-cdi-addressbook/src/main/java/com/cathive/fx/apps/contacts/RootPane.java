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

import com.cathive.fx.apps.contacts.model.Company;
import com.cathive.fx.apps.contacts.model.Contact;
import com.cathive.fx.apps.contacts.model.Person;
import com.cathive.fx.inject.core.FXMLComponent;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import javax.annotation.PostConstruct;

import static javafx.beans.binding.Bindings.createBooleanBinding;

/**
 * Root pane
 * @author Benjamin P. Jung
 */
@FXMLComponent(location = "RootPane.fxml", resources = "com.cathive.fx.apps.contacts.Messages")
public class RootPane extends AnchorPane {

    @FXML private ToolBar toolBar;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private ContactListView contactListView;

    @PostConstruct
    protected void init() {

        final Person p1 = Contact.create(Person.class);
        p1.setFirstName("John");
        p1.setLastName("Doe");

        final Person p2 = Contact.create(Person.class);
        p2.setFirstName("Max");
        p2.setLastName("Mustermann");

        final Company c1 = Contact.create(Company.class);
        c1.setName("Monster Inc.");

        contactListView.getItems().add(p1);
        contactListView.getItems().add(p2);
        contactListView.getItems().addAll(c1);

        removeButton.disableProperty().bind(createBooleanBinding(() -> contactListView.getSelectionModel().getSelectedItem() == null,
                        contactListView.getSelectionModel().selectedItemProperty()));

    }

}
