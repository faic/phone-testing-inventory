package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.PhoneDetailedDTO;
import org.phonetesting.persistance.Booking;
import org.phonetesting.persistance.Phone;

import java.util.Optional;

@Mapper
public interface PhoneDetailedMapper {
    public static final PhoneDetailedMapper INSTANCE = Mappers.getMapper(PhoneDetailedMapper.class);

    default PhoneDetailedDTO toPhoneDetailedDTO(Phone phone, Booking activeBooking) {
        return new PhoneDetailedDTO(
                PhoneMapper.INSTANCE.toPhoneDTO(phone),
                Optional.ofNullable(activeBooking).map(ActiveBookingMapper.INSTANCE::toBookingDTO).orElse(null)
        );
    }
}
