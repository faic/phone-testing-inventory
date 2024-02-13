package org.phonetesting.exceptions;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(long bookingId) {
        super(String.format("Booking with id %d not found", bookingId));
    }
}
