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
public class StringConverterRetriever {
    private static Map<Class<?>, Class<? extends StringConverter<?>>> converters = new HashMap<>();
    static  {
        // String
        converters.put(String.class, DefaultStringConverter.class);

        // Primitives
        converters.put(boolean.class, BooleanStringConverter.class);
        converters.put(byte.class, ByteStringConverter.class);
        converters.put(char.class, CharacterStringConverter.class);
        converters.put(double.class, DoubleStringConverter.class);
        converters.put(float.class, FloatStringConverter.class);
        converters.put(int.class, IntegerStringConverter.class);
        converters.put(long.class, LongStringConverter.class);
        converters.put(short.class, ShortStringConverter.class);

        // Primitive wrappers
        converters.put(Boolean.class, BooleanStringConverter.class);
        converters.put(Byte.class, ByteStringConverter.class);
        converters.put(Character.class, CharacterStringConverter.class);
        converters.put(Double.class, DoubleStringConverter.class);
        converters.put(Float.class, FloatStringConverter.class);
        converters.put(Integer.class, IntegerStringConverter.class);
        converters.put(Long.class, LongStringConverter.class);
        converters.put(Short.class, ShortStringConverter.class);

        // Other types
        converters.put(BigDecimal.class, BigDecimalStringConverter.class);
        converters.put(BigInteger.class, BigIntegerStringConverter.class);
    }

    /**
     * Retrieve a converter for the fiven class
     * @param valueClass the class to retrieve a converter for
     * @return a matching converter, <code>null</code> if none found
     */
    public static Class<? extends StringConverter<?>> retrieveConverterFor(Class<?> valueClass) {
        return converters.get(valueClass);
    }
}
