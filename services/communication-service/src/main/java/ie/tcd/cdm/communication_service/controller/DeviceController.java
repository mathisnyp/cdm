package ie.tcd.cdm.communication_service.controller;

import ie.tcd.cdm.communication_service.dto.LinkDeviceDTO;
import ie.tcd.cdm.communication_service.dto.RegisterDeviceDTO;
import ie.tcd.cdm.communication_service.dto.UpdateUserGroupDTO;
import ie.tcd.cdm.communication_service.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@AllArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDevice(@RequestBody RegisterDeviceDTO registerDeviceDTO) {
        deviceService.registerDevice(registerDeviceDTO);
    }

    @PostMapping("/link")
    @ResponseStatus(HttpStatus.CREATED)
    public void linkDevice(@RequestBody LinkDeviceDTO linkDeviceDTO) {
        deviceService.linkDevice(linkDeviceDTO);
    }

    @PostMapping("/update/group")
    public void updateUserGroup(@RequestBody UpdateUserGroupDTO updateUserGroupDTO) {
        deviceService.updateUserGroup(updateUserGroupDTO);
    }
}
