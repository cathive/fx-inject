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

import com.cathive.fx.apps.contacts.model.Person;
import com.cathive.fx.inject.core.FXMLComponent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

/**
 * List cell to display contacts.
 * @author Benjamin P. Jung
 */
@Dependent
@FXMLComponent(location = "ContactListCell.fxml")
public class ContactListCell extends HBox {

    public static final String PERSON_PROPERTY = "person";

    private final ObjectProperty<Person> person = new SimpleObjectProperty<>(this, PERSON_PROPERTY);

    @FXML
    private Label displayNameLabel;
    @FXML
    private ImageView contactNameLabel;

    public ObjectProperty<Person> personProperty() {
        return this.person;
    }

    public Person getPerson() {
        return this.person.get();
    }


    @PostConstruct
    protected void init() {
        this.displayNameLabel.textProperty().bind(
                Bindings.createObjectBinding(
                        () -> this.getPerson() == null ? "" : this.getPerson().getDisplayName(),
                        this.person));
    }


}
