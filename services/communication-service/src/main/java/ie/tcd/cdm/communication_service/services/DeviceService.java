package ie.tcd.cdm.communication_service.services;

import ie.tcd.cdm.communication_service.dto.LinkDeviceDTO;
import ie.tcd.cdm.communication_service.dto.RegisterDeviceDTO;
import ie.tcd.cdm.communication_service.dto.UpdateUserGroupDTO;
import ie.tcd.cdm.communication_service.jpa.PushNotificationTokenRepository;
import ie.tcd.cdm.communication_service.jpa.UserPushNotificationIdRepository;
import ie.tcd.cdm.communication_service.model.PushNotificationToken;
import ie.tcd.cdm.communication_service.model.UserIdTokenId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceService {
    private final PushNotificationTokenRepository pushNotificationTokenRepository;
    private final UserPushNotificationIdRepository userIdTokenIdRepository;

    public void registerDevice(RegisterDeviceDTO registerDeviceDTO) {
        pushNotificationTokenRepository.save(PushNotificationToken.builder()
                .token(registerDeviceDTO.token())
                .build());
    }

    public void linkDevice(LinkDeviceDTO linkDeviceDTO) {
        PushNotificationToken pushNotificationToken = pushNotificationTokenRepository.findByToken(linkDeviceDTO.token());

        UserIdTokenId oldUserIdTokenId = userIdTokenIdRepository.getUserIdTokenIdByUserID(linkDeviceDTO.userId());
        if (oldUserIdTokenId != null) {
            userIdTokenIdRepository.delete(oldUserIdTokenId);
            PushNotificationToken oldPushNotificationToken = pushNotificationTokenRepository.findByToken(oldUserIdTokenId.getPushNotificationToken().getToken());
            pushNotificationTokenRepository.delete(oldPushNotificationToken);
        }

        userIdTokenIdRepository.save(UserIdTokenId.builder()
                .userID(linkDeviceDTO.userId())
                .pushNotificationToken(pushNotificationToken)
                .build());
    }

    public void updateUserGroup(UpdateUserGroupDTO updateUserGroupDTO) {
        UserIdTokenId userIdTokenId = userIdTokenIdRepository.getUserIdTokenIdByUserID(updateUserGroupDTO.userId());
        userIdTokenId.setGroupID(updateUserGroupDTO.newGroupId());
        userIdTokenIdRepository.save(userIdTokenId);
    }
}
