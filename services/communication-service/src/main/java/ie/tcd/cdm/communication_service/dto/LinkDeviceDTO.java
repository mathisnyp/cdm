package ie.tcd.cdm.communication_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@JsonDeserialize(builder = LinkDeviceDTO.LinkDeviceDTOBuilder.class)
@Builder
public record LinkDeviceDTO(
        @NotNull
        @JsonProperty
        String token,
        @NotNull
        @JsonProperty
        long userId
) {
}
