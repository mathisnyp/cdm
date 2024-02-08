package ie.tcd.cdm.incident_service.mapper;

import ie.tcd.cdm.incident_service.dto.UpdateReportDTO;
import ie.tcd.cdm.incident_service.model.Report;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReportFromDTO(UpdateReportDTO dto, @MappingTarget Report entity);
}
