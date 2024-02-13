package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.PhoneDTO;
import org.phonetesting.persistance.Phone;

@Mapper
public interface PhoneMapper {
    public static final PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    default PhoneDTO toPhoneDTO(Phone phone) {
        return new PhoneDTO(
                phone.getId(),
                PhoneSpecMapper.INSTANCE.toPhoneSpecDTO(phone),
                OsMapper.INSTANCE.toOsDTO(phone),
                phone.isAvailable()
        );
    }
}
