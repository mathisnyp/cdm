package ie.tcd.cdm.communication_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@JsonDeserialize(builder = RegisterDeviceDTO.RegisterDeviceDTOBuilder.class)
@Builder
public record RegisterDeviceDTO(
        @NotNull
        @JsonProperty
        String token
) {
}
