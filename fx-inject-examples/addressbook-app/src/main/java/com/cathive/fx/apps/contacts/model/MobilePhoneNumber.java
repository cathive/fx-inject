package com.cathive.fx.apps.contacts.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * See <a href="http://en.wikipedia.org/wiki/MSISDN">http://en.wikipedia.org/wiki/MSISDN</a>
 * for further details about MSISDNs.
 * @author Benjamin P. Jung
 */
@Entity
@Table("phone_mobile")
@DiscriminatorValue(value = "M")
public class MobilePhoneNumber extends PhoneNumber
                               implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;

    // <editor-fold desc="Property: national destination code (NDC)">
    public static final String NATIONAL_DESTINATION_CODE_PROPERTY = "nationalDestinationCode";
    // </editor-fold>

    // <editor-fold desc="Property: subscriber number property (SN)">
    public static final String SUBSCRIBER_NUMBER_PROPERTY = "subscriberNumber";
    // </editor-fold>
}
