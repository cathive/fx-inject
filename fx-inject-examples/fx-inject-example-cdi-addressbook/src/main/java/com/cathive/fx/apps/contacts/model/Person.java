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

package com.cathive.fx.apps.contacts.model;

import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Encapsulates a person entity.
 *
 * @author Benjamin P. Jung
 */
@SuppressWarnings("UnusedDeclaration")
public class Person extends Contact
                    implements Serializable {

    public static final String LAST_NAME_PROPERTY = "lastName";
    public static final String FIRST_NAME_PROPERTY = "firstName";
    public static final String SEX_PROPERTY = "sex";
    public static final String SALUTATION_PROPERTY = "salutation";

    private final StringProperty lastName = new SimpleStringProperty(this, LAST_NAME_PROPERTY);
    private final StringProperty firstName = new SimpleStringProperty(this, FIRST_NAME_PROPERTY);
    private final ObjectProperty<Sex> sex = new SimpleObjectProperty<>(this, SEX_PROPERTY);
    private final ReadOnlyStringWrapper salutation = new ReadOnlyStringWrapper(this, SALUTATION_PROPERTY);

    public Person() {

        super();

        this.displayName.bind(
                Bindings.createObjectBinding(() -> this.getLastName() + ", " + this.getFirstName(),
                        this.lastName,
                        this.firstName));

        this.salutation.bind(
                Bindings.createStringBinding(() -> this.getSex() == Sex.MALE ? "Mr." : this.getSex() == Sex.FEMALE ? "Mrs." : "",
                        this.sex));

    }


    public Person(@NamedArg(FIRST_NAME_PROPERTY) final String firstName,
                  @NamedArg(LAST_NAME_PROPERTY) final String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }


    // <editor-fold defaultstate="collapsed" desc="Bean Properties">

    public StringProperty lastNameProperty() {
        return this.lastName;
    }

    public String getLastName() {
        return this.lastName.get();
    }

    public void setLastName(final String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty firstNameProperty() {
        return this.firstName;
    }

    public String getFirstName() {
        return this.firstName.get();
    }

    public void setFirstName(final String firstName) {
        this.firstName.set(firstName);
    }

    public Sex getSex() {
        return this.sex.get();
    }

    public void setSex(final Sex sex) {
        this.sex.set(sex);
    }

    public ObjectProperty<Sex> sexProperty() {
        return this.sex;
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Pseudo / compound properties">

    public ReadOnlyStringProperty salutationProperty() {
        return this.salutation.getReadOnlyProperty();
    }

    public String getSalutation() {
        return this.salutation.get();
    }

    // </editor-fold>
}
