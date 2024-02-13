package org.phonetesting.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super(String.format("Booking with email %s not found", email));
    }
}
