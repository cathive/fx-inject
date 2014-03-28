package com.cathive.fx.apps.contacts.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * See <a href="http://en.wikipedia.org/wiki/MSISDN">http://en.wikipedia.org/wiki/MSISDN</a>
 * for further details about MSISDNs.
 * @author Benjamin P. Jung
 */
@Entity
@Table(name = "phone_mobile")
@DiscriminatorValue(value = "M")
public class MobilePhoneNumber extends PhoneNumber
                               implements Serializable {

    /** @see java.io.Serializable */
    private static final long serialVersionUid = 1L;

    // <editor-fold desc="Property: national destination code (NDC)">
    public static final String NATIONAL_DESTINATION_CODE_PROPERTY = "nationalDestinationCode";
    private final StringProperty nationalDestinationCode = new SimpleStringProperty(this. NATIONAL_DESTINATION_CODE_PROPERTY);
    @Basic
    @Column(name = "ndc")
    public String getNationalDestinationCode() {
        return this.nationalDestinationCode.get();
    }
    public void setNationalDestinationCode(final String nationalDestinationCode) {
        this.nationalDestinationCode.set(nationalDestinationCode);
    }
    public StringProperty nationalDestinationCodeProperty() {
        return this.nationalDestinationCode;
    }
    // </editor-fold>

    // <editor-fold desc="Property: subscriber number property (SN)">
    public static final String SUBSCRIBER_NUMBER_PROPERTY = "subscriberNumber";
    private final StringProperty subscriberNumber = new SimpleStringProperty(this, SUBSCRIBER_NUMBER_PROPERTY);
    @Basic
    @Column(name = "sn")
    public String getSubscriberNumber() {
        return this.subscriberNumber.get();
    }
    public void setSubscriberNumber(final String subscriberNumber) {
        this.subscriberNumber.set(subscriberNumber);
    }
    public StringProperty subscriberNumberProperty() {
        return this.subscriberNumber;
    }
    // </editor-fold>
}
