package org.phonetesting.dtos;

public record PhoneDTO(long id, PhoneSpecDTO spec, OsDTO os, boolean available) { }
