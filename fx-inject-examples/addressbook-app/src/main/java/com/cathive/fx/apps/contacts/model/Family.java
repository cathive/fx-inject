package com.cathive.fx.apps.contacts.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
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

}
