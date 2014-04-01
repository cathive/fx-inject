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

/**
 * Gender definition according to ISO/IEC 5218 ("Codes for the representation of human sexes").
 * @author Benjamin P. Jung
 */
public enum Sex {

    NOT_KNOWN(0),

    MALE(1),

    FEMALE(2),

    NOT_APPLICABLE(9);

    /** Numeric value according to ISO/IEC 5218. */
    private final int value;
    public int getValue() { return this.value; }
    private Sex(final int value) {
        this.value = value;
    }

    public static Sex valueOf(final int value) {
        switch (value) {
            case 0: return NOT_KNOWN;
            case 1: return MALE;
            case 2: return FEMALE;
            case 9: return NOT_APPLICABLE;
            default:
                throw new IllegalArgumentException();
        }
    }

}
