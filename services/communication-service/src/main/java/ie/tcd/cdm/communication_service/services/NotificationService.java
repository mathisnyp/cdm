package ie.tcd.cdm.communication_service.services;

import ie.tcd.cdm.communication_service.dto.CreateNotificationDTO;
import ie.tcd.cdm.communication_service.dto.UpdateNotificationDTO;
import ie.tcd.cdm.communication_service.jpa.NotificationRepository;
import ie.tcd.cdm.communication_service.mapper.NotificationMapper;
import ie.tcd.cdm.communication_service.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    public void updateNotification(long id, UpdateNotificationDTO notificationDTO) {
        Notification notification = this.getNotificationById(id);
        mapper.updateNotificationFromDTO(notificationDTO, notification);
        notificationRepository.save(notification);
    }

    public void deleteNotification(long id) {
        notificationRepository.deleteById(id);
    }

    public void createNotification(CreateNotificationDTO notificationDTO) {
        notificationRepository.save(Notification.builder()
                        .messageBody(notificationDTO.messageBody())
                        .messageHeader(notificationDTO.messageHeader())
                        .messageSender(notificationDTO.messageSender())
                        .receiver(notificationDTO.receiver())
                        .receiverGroup(notificationDTO.receiverGroup())
                        .scheduledTime(notificationDTO.scheduledTime())
                .build());
    }

    public Notification getNotificationById(long id) {
        return notificationRepository.getReferenceById(id);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}