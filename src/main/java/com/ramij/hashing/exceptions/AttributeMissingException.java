package com.ramij.hashing.exceptions;

public class AttributeMissingException extends RuntimeException {
    String message;

    public AttributeMissingException(String s) {
        super(s);
        this.message = s;
    }
}
