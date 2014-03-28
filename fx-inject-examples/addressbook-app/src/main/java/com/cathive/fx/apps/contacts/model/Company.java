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
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
@Entity
@Table(name = "contact_company")
@DiscriminatorValue(value = "C")
public class Company extends Contact
                     implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;

    // <editor-fold desc="Property: name">
    public static final String NAME_PROPERTY = "name";
    private final StringProperty name = new SimpleStringProperty(this, NAME_PROPERTY);
    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name.get();
    }
    public void setName(final String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return this.name;
    }
    // </editor-fold>


    public Company() {
        super();
    }

    public Company(@NamedArg(NAME_PROPERTY) final String name) {
        this();
        this.setName(name);
    }

}
