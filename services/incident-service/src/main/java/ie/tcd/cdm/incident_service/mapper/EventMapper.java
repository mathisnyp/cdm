package ie.tcd.cdm.incident_service.mapper;

import ie.tcd.cdm.incident_service.dto.UpdateEventDTO;
import ie.tcd.cdm.incident_service.model.Event;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDTO(UpdateEventDTO dto, @MappingTarget Event entity);
}
