package ie.tcd.cdm.incident_service.apis.communication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Map;

@Builder
@JsonDeserialize(builder = BroadcastNotificationFromUserDTO.BroadcastNotificationFromUserDTOBuilder.class)
public record BroadcastNotificationFromUserDTO(
        @NotNull
        @JsonProperty
        String messageBody,
        @NotNull
        @JsonProperty
        String messageHeader,
        @NotNull
        @JsonProperty
        long from,

        @JsonProperty
        Map<String, Object> data

        ) {

}
