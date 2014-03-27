package com.cathive.fx.apps.contacts.model;

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
