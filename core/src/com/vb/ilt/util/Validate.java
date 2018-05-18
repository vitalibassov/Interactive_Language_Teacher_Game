package com.vb.ilt.util;

public class Validate {

    public static final String DEFAULT_IS_NULL_MESSAGE = "The validated object is null.";

    private Validate(){}

    public static <T> void notNull(T object){
        notNull(object, DEFAULT_IS_NULL_MESSAGE);
    }

    public static <T> void notNull(T object, String message){
        if(object == null){
            throw new NullPointerException(message);
        }
    }
}
