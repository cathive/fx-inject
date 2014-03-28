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

import com.cathive.fx.apps.contacts.model.converter.ImageConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
@Entity
@Table(name = "contact")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "?")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Contact extends AbstractEntity
                              implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUID = 1L;

    // <editor-fold desc="Property: photo">
    public static final String PHOTO_PROPERTY = "photo";
    private final ObjectProperty<Image> photo = new SimpleObjectProperty<>(this, PHOTO_PROPERTY);
    public ObjectProperty<Image> photoProperty() {
        return this.photo;
    }
    @Column(name = "photo_data")
    @Convert(converter = ImageConverter.class)
    public Image getPhoto() {
        return this.photo.get();
    }
    public void setPhoto(final Image photo) {
        this.photo.set(photo);
    }
    // </editor-fold>


    public Contact() {
        super();
    }


    @Transient
    public abstract ContactType getType();

    public static <T extends Contact> T create(final Class<T> contactType) {
        final T contact;
        try {
            contact = contactType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        return contact;
    }

}
