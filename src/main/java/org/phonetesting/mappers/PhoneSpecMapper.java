package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.PhoneSpecDTO;
import org.phonetesting.persistence.Phone;

@Mapper
public interface PhoneSpecMapper {
    public static final PhoneSpecMapper INSTANCE = Mappers.getMapper(PhoneSpecMapper.class);
    PhoneSpecDTO toPhoneSpecDTO(Phone phone);
}
