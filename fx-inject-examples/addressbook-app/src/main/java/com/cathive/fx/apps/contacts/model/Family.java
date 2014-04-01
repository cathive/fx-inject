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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
@Entity
@Table(name = "contact_family")
@DiscriminatorValue(value = "F")
public class Family extends Contact
                    implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;


    public Family() {
        super();
    }

    public Family(@NamedArg(FAMILIY_NAME_PROPERTY) final String familyName) {
        this();
        this.setFamilyName(familyName);
    }


    @Override
    public ContactType getType() {
        return ContactType.FAMILY;
    }


    // <editor-fold desc="Property: family name">
    public static final String FAMILIY_NAME_PROPERTY = "familyName";
    public final StringProperty familyName = new SimpleStringProperty(this, FAMILIY_NAME_PROPERTY);
    @Basic
    @Column(name = "family_name", nullable = false)
    public String getFamilyName() {
        return this.familyName.get();
    }
    public void setFamilyName(final String familyName) {
        this.familyName.set(familyName);
    }
    public StringProperty familyNameProperty() {
        return this.familyName;
    }
    // </editor-fold>

}
