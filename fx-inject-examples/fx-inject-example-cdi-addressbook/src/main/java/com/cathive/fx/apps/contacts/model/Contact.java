package com.cathive.fx.apps.contacts.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
public abstract class Contact implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUID = 1L;

    public static final String DISPLAY_NAME_PROPERTY = "displayName";
    protected final ReadOnlyStringWrapper displayName = new ReadOnlyStringWrapper(this, DISPLAY_NAME_PROPERTY);
    public ReadOnlyStringProperty displayNameProperty() { return this.displayName.getReadOnlyProperty(); }
    public String getDisplayName() { return this.displayName.get(); }

}
