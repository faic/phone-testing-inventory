package org.phonetesting.dtos;

import java.time.Instant;

public record BookingDTO(long id, PhoneSpecDTO spec, UserDTO user, Instant bookedAt, Instant returnedAt) { }
