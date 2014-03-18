package com.cathive.fx.apps.contacts.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
public class Company extends Contact
                     implements Serializable {

    public static final String NAME_PROPERTY = "name";
    private final StringProperty name = new SimpleStringProperty(this, NAME_PROPERTY);
    public String getName() { return this.name.get(); }
    public void setName(final String name) { this.name.set(name); }
    public StringProperty nameProperty() { return this.name; }

    {
        // Display name == name for companies at the moment.
        this.displayName.bind(this.name);
    }

}
