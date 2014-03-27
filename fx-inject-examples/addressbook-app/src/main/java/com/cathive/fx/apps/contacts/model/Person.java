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

import com.cathive.fx.apps.contacts.model.converter.SexConverter;
import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Encapsulates a person entity.
 *
 * @author Benjamin P. Jung
 */
@Entity
@Table(name = "contact_person")
@DiscriminatorValue(value = "P")
public class Person extends Contact
                    implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;

    // <editor-fold desc="Property: last name">
    public static final String LAST_NAME_PROPERTY = "lastName";
    private final StringProperty lastName = new SimpleStringProperty(this, LAST_NAME_PROPERTY);
    public StringProperty lastNameProperty() {
        return this.lastName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName.get();
    }

    public void setLastName(final String lastName) {
        this.lastName.set(lastName);
    }
    // </editor-fold>

    // <editor-fold desc="Property: first name">
    public static final String FIRST_NAME_PROPERTY = "firstName";
    private final StringProperty firstName = new SimpleStringProperty(this, FIRST_NAME_PROPERTY);
    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName.get();
    }
    public void setFirstName(final String firstName) {
        this.firstName.set(firstName);
    }
    public StringProperty firstNameProperty() {
        return this.firstName;
    }
    // </editor-fold>

    // <editor-fold desc="Property: sex">
    public static final String SEX_PROPERTY = "sex";
    private final ObjectProperty<Sex> sex = new SimpleObjectProperty<>(this, SEX_PROPERTY, Sex.NOT_KNOWN);
    @Column(name = "sex")
    @Convert(converter = SexConverter.class)
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

    // <editor-fold desc="Pseudo-property: salutation">
    public static final String SALUTATION_PROPERTY = "salutation";
    private final ReadOnlyStringWrapper salutation = new ReadOnlyStringWrapper(this, SALUTATION_PROPERTY);
    @Transient
    public String getSalutation() {
        return this.salutation.get();
    }
    public ReadOnlyStringProperty salutationProperty() {
        return this.salutation.getReadOnlyProperty();
    }
    // </editor-fold>



    public Person() {
        super();
        // TODO Once the lines belows have been uncommented, JPA/EclipseLink in conjunction with JavaDB/Derby
        //      no longer works. :-(
//        this.displayName.bind(
//                Bindings.createObjectBinding(() -> this.getLastName() + ", " + this.getFirstName(),
//                        this.lastName,
//                        this.firstName)
//        );
//        this.salutation.bind(
//                Bindings.createStringBinding(() -> this.getSex() == Sex.MALE ? "Mr." : this.getSex() == Sex.FEMALE ? "Mrs." : "",
//                        this.sex));
    }


    public Person(@NamedArg(FIRST_NAME_PROPERTY) final String firstName,
                  @NamedArg(LAST_NAME_PROPERTY) final String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

}
