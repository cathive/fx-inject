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
