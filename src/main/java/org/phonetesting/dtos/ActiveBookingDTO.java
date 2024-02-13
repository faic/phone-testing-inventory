package org.phonetesting.dtos;

import java.time.Instant;

public record ActiveBookingDTO(long id, UserDTO user, Instant bookedAt) { }
