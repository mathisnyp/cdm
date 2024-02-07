package ie.tcd.cdm.communication_service.controller;

import ie.tcd.cdm.communication_service.dto.CreateNotificationDTO;
import ie.tcd.cdm.communication_service.dto.UpdateNotificationDTO;
import ie.tcd.cdm.communication_service.model.Notification;
import ie.tcd.cdm.communication_service.services.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable long id) {
        return notificationService.getNotificationById(id);
    }

    @PostMapping
    public void createNotification(@RequestBody CreateNotificationDTO notification) {
        notificationService.createNotification(notification);
    }

    @PutMapping("/{id}")
    public void updateNotification(@PathVariable long id, @RequestBody UpdateNotificationDTO notification) {
        notificationService.updateNotification(id, notification);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable long id) {
        notificationService.deleteNotification(id);
    }


}
