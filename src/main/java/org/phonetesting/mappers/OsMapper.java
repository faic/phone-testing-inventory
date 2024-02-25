package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.OsDTO;
import org.phonetesting.persistence.Phone;

@Mapper
public interface OsMapper {
    public static final OsMapper INSTANCE = Mappers.getMapper(OsMapper.class);

    @Mapping(target = "version", source = "osVersion")
    OsDTO toOsDTO(Phone phone);
}
