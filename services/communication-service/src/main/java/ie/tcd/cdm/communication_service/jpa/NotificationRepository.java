package ie.tcd.cdm.communication_service.jpa;

import ie.tcd.cdm.communication_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.messageSender = :senderID")
    Collection<Notification> getAllNotificationsBySender(@Param("senderID") Long senderID);
}
