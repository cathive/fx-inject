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

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by benjamin.jung on 3/27/2014.
 */
@Entity
@Table(name = "phone_number")
public class PhoneNumber implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;

    public static final String ID_PROPERTY = "id";
    private final ReadOnlyLongWrapper id = new ReadOnlyLongWrapper(this, ID_PROPERTY);
    public ReadOnlyLongProperty idProperty() { return this.id; }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return this.id.get(); }
    public void setId(final Long id) { this.id.set(id); }

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

}
