package org.phonetesting.exceptions;

public class PhoneNotFoundException extends RuntimeException {

    public PhoneNotFoundException(long phoneId) {
        super(String.format("Phone with id %d not found", phoneId));
    }
}
