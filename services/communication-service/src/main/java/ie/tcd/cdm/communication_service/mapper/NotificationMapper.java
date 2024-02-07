package ie.tcd.cdm.communication_service.mapper;

import ie.tcd.cdm.communication_service.dto.UpdateNotificationDTO;
import ie.tcd.cdm.communication_service.model.Notification;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNotificationFromDTO(UpdateNotificationDTO dto, @MappingTarget Notification entity);
}
