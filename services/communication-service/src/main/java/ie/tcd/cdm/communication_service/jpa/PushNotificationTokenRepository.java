package ie.tcd.cdm.communication_service.jpa;

import ie.tcd.cdm.communication_service.model.PushNotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, Long> {
    PushNotificationToken findByToken(String token);
}
