package ie.tcd.cdm.incident_service.mapper;

import ie.tcd.cdm.incident_service.dto.UpdateIncidentDTO;
import ie.tcd.cdm.incident_service.model.Incident;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IncidentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateIncidentFromDTO(UpdateIncidentDTO dto, @MappingTarget Incident entity);
}