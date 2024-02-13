package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.ActiveBookingDTO;
import org.phonetesting.persistance.Booking;

@Mapper
public interface ActiveBookingMapper {
    public static final ActiveBookingMapper INSTANCE = Mappers.getMapper(ActiveBookingMapper.class);

    default ActiveBookingDTO toBookingDTO(Booking booking) {
        return new ActiveBookingDTO(
                booking.getId(),
                UserMapper.INSTANCE.toUserDTO(booking.getUser()),
                booking.getBookedAt()
        );
    }
}

