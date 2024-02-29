package ie.tcd.cdm.communication_service.jpa;

import ie.tcd.cdm.communication_service.model.PushNotificationToken;
import ie.tcd.cdm.communication_service.model.UserIdTokenId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPushNotificationIdRepository extends JpaRepository<UserIdTokenId, Long> {
    UserIdTokenId getUserIdTokenIdByUserID(long userID);

    List<UserIdTokenId> getUserIdTokenIdByGroupID(Long groupId);

    @Query("SELECT userIdTokenId FROM UserIdTokenId userIdTokenId WHERE userIdTokenId.pushNotificationToken = :pushNotificationToken")
    UserIdTokenId getUserIdTokenIdByPushNotificationToken(@Param("pushNotificationToken") PushNotificationToken pushNotificationToken);
}
