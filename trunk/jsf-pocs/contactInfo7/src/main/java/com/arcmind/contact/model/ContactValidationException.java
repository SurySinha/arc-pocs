package com.arcmind.contact.model;

@SuppressWarnings("serial")
public class ContactValidationException extends Exception {
    public ContactValidationException() {
    }

    public ContactValidationException(String message) {
        super(message);
    }
}
