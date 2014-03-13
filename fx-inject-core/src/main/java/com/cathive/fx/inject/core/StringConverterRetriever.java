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

package com.cathive.fx.inject.core;

import javafx.util.StringConverter;
import javafx.util.converter.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Convenience class that encapsulates the construction of a string converter map
 * @author Alexander Erben (a_erben@outlook.com)
 */
final class StringConverterRetriever {
    private static Map<Class<?>, Class<? extends StringConverter<?>>> CONVERTERS = new HashMap<>();
    static  {

        // String
        CONVERTERS.put(String.class, DefaultStringConverter.class);

        // Primitives
        CONVERTERS.put(boolean.class, BooleanStringConverter.class);
        CONVERTERS.put(byte.class, ByteStringConverter.class);
        CONVERTERS.put(char.class, CharacterStringConverter.class);
        CONVERTERS.put(double.class, DoubleStringConverter.class);
        CONVERTERS.put(float.class, FloatStringConverter.class);
        CONVERTERS.put(int.class, IntegerStringConverter.class);
        CONVERTERS.put(long.class, LongStringConverter.class);
        CONVERTERS.put(short.class, ShortStringConverter.class);

        // Primitive wrappers
        CONVERTERS.put(Boolean.class, BooleanStringConverter.class);
        CONVERTERS.put(Byte.class, ByteStringConverter.class);
        CONVERTERS.put(Character.class, CharacterStringConverter.class);
        CONVERTERS.put(Double.class, DoubleStringConverter.class);
        CONVERTERS.put(Float.class, FloatStringConverter.class);
        CONVERTERS.put(Integer.class, IntegerStringConverter.class);
        CONVERTERS.put(Long.class, LongStringConverter.class);
        CONVERTERS.put(Short.class, ShortStringConverter.class);

        // Other types
        CONVERTERS.put(BigDecimal.class, BigDecimalStringConverter.class);
        CONVERTERS.put(BigInteger.class, BigIntegerStringConverter.class);

    }

    /**
     * Retrieve a converter for the given class
     * @param valueClass the class to retrieve a converter for
     * @return a matching converter, {@code null} if none found
     */
    public static Class<? extends StringConverter<?>> retrieveConverterFor(final Class<?> valueClass) {
        return CONVERTERS.get(valueClass);
    }


    // Private c-tor to avoid instantiation.
    private StringConverterRetriever() { /* Intentionally left empty. */ }

}
