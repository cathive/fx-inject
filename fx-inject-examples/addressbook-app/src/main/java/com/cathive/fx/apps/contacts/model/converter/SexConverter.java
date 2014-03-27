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

package com.cathive.fx.apps.contacts.model.converter;

import com.cathive.fx.apps.contacts.model.Sex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Benjamin P. Jung
 */
@Converter
public class SexConverter implements AttributeConverter<Sex, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Sex attribute) {
        return attribute == null ? Sex.NOT_KNOWN.getValue() :  Integer.valueOf(attribute.getValue());
    }

    @Override
    public Sex convertToEntityAttribute(final Integer dbData) {
        return dbData == null ? Sex.NOT_KNOWN : Sex.valueOf(dbData.intValue());
    }

}
