package com.cathive.fx.apps.contacts.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * @author Benjamin P. Jung
 */
public class Person implements Serializable {

    public static final String LAST_NAME_PROPERTY = "lastName";
    public static final String FIRST_NAME_PROPERTY = "firstName";
    public static final String DISPLAY_NAME_PROPERTY = "displayName";

    private final StringProperty lastName = new SimpleStringProperty(this, LAST_NAME_PROPERTY);
    public StringProperty lastNameProperty() { return this.lastName; }
    public String getLastName() { return this.lastName.get(); }
    public void setLastName(final String lastName) { this.lastName.set(lastName); }

    private final StringProperty firstName = new SimpleStringProperty(this, FIRST_NAME_PROPERTY);
    public StringProperty firstNameProperty() { return this.firstName; }
    public String getFirstName() { return this.firstName.get(); }
    public void setFirstName(final String firstName) { this.firstName.set(firstName); }

    public final ReadOnlyStringWrapper displayName = new ReadOnlyStringWrapper(this, DISPLAY_NAME_PROPERTY);
    public ReadOnlyStringProperty displayNameProperty() { return this.displayName.getReadOnlyProperty(); }
    public String getDisplayName() { return this.displayName.get(); }

    public Person() {
        super();
        this.displayName.bind(
                Bindings.createObjectBinding(() -> this.getLastName() + ", " + this.getFirstName(),
                this.lastName,
                this.firstName));
    }

}
