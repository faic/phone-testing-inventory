package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.BookingDTO;
import org.phonetesting.persistence.Booking;

@Mapper
public interface BookingMapper {
    public static final BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    default BookingDTO toBookingDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                PhoneSpecMapper.INSTANCE.toPhoneSpecDTO(booking.getPhone()),
                UserMapper.INSTANCE.toUserDTO(booking.getUser()),
                booking.getBookedAt(),
                booking.getReturnedAt());
    }
}
