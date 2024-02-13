package org.phonetesting.exceptions;


public class BookingAlreadyFinishedException extends RuntimeException {

    public BookingAlreadyFinishedException(long bookingId) {
        super(String.format("Booking with id %d has already been finished", bookingId));
    }
}
