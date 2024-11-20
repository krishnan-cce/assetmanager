package com.udyata.linentrack.linentrack.mapper;

import com.udyata.linentrack.linentrack.entity.locationmaster.LocationMaster;
import com.udyata.linentrack.linentrack.payload.locationmasterdto.LocationMasterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMasterMapper {
    LocationMasterMapper INSTANCE = Mappers.getMapper(LocationMasterMapper.class);

    LocationMasterDTO toDTO(LocationMaster locationMaster);
    LocationMaster toEntity(LocationMasterDTO locationMasterDTO);

    void updateEntityFromDTO(LocationMasterDTO dto, @MappingTarget LocationMaster entity);
}
