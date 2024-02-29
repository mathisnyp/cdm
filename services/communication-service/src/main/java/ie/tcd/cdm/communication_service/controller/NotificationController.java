package ie.tcd.cdm.communication_service.controller;

import ie.tcd.cdm.communication_service.dto.*;
import ie.tcd.cdm.communication_service.model.Notification;
import ie.tcd.cdm.communication_service.services.NotificationService;
import io.github.jav.exposerversdk.PushClientException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/send")
    public void sendNotification(@RequestBody SendNotificationUserToUserDTO sendNotificationUserToUserDTO) throws PushClientException {
        this.notificationService.sendNotification(sendNotificationUserToUserDTO);
    }

    @PostMapping("/broadcast")
    public void broadcastNotification(@RequestBody BroadcastNotificationFromUserDTO broadcastNotificationFromUserDTO) throws PushClientException {
        this.notificationService.broadcastNotification(broadcastNotificationFromUserDTO);
    }

    @PostMapping("/broadcast/group")
    public void broadcastNotificationGroup(@RequestBody BroadcastNotificationUserToGroupDTO broadcastNotificationUserToGroupDTO) throws PushClientException {
        this.notificationService.broadcastNotificationToGroup(broadcastNotificationUserToGroupDTO);
    }
}
