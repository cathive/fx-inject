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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
@Entity
@Table(name = "phone")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "?")
@Inheritance(strategy=InheritanceType.JOINED)
public class PhoneNumber extends AbstractEntity
                         implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;

    // <editor-fold desc="Property: country code (CC)">
    private final StringProperty countryCode = new SimpleStringProperty(this, "countryCode");
    @Column(name = "cc")
    public String getCountryCode() {
        return this.countryCode.get();
    }
    public void setCountryCode(final String countryCode) {
        this.countryCode.set(countryCode);
    }
    public StringProperty countryCodeProperty() {
        return this.countryCode;
    }
    // </editor-fold>

}
