package org.phonetesting.exceptions;

public class PhoneUnavailableException extends RuntimeException {

    public PhoneUnavailableException() {
        super("Phone already booked");
    }
}
