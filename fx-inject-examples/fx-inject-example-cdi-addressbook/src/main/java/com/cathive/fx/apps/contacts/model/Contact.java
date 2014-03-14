package com.cathive.fx.apps.contacts.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Benjamin P. Jung
 */
public abstract class Contact implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUID = 1L;

    public static final String UID_PROPERTY = "uid";
    protected final ReadOnlyObjectWrapper<UUID> uid = new ReadOnlyObjectWrapper(this, UID_PROPERTY);
    public ReadOnlyObjectProperty uidProperty() { return this.uid; }
    public UUID getUid() { return this.uid.get(); }
    protected void setUid(final UUID uid) { this.uid.set(uid); }

    public static final String DISPLAY_NAME_PROPERTY = "displayName";
    protected final ReadOnlyStringWrapper displayName = new ReadOnlyStringWrapper(this, DISPLAY_NAME_PROPERTY);
    public ReadOnlyStringProperty displayNameProperty() { return this.displayName.getReadOnlyProperty(); }
    public String getDisplayName() { return this.displayName.get(); }

    public static final String PHOTO_PROPERTY = "photo";
    protected final ObjectProperty<Image> photo = new SimpleObjectProperty<>(this, PHOTO_PROPERTY);
    public ObjectProperty photoProperty() { return this.photo; }
    public Image getPhoto() { return this.photo.get(); }
    public void setPhoto(final Image photo) { this.photo.set(photo); }


    protected Contact() {
        super();
    }

    public static <T extends Contact> T create(final Class<T> contactType) {
        final T contact;
        try {
            contact = contactType.newInstance();
            contact.setUid(UUID.randomUUID());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        return contact;
    }

}
