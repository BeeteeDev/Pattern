package com.google.development.leonmerten.pattern.commons.concurrent;

public class FunctionValidator {
    public static boolean notNullOrEmpty(Object set) {
        if(set != null)
            return true;
        return false;
    }
}
