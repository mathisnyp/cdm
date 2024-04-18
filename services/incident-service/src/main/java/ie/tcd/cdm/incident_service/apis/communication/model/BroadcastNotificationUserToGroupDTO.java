package ie.tcd.cdm.incident_service.apis.communication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Map;

@Builder
@JsonDeserialize(builder = BroadcastNotificationUserToGroupDTO.BroadcastNotificationUserToGroupDTOBuilder.class)
public record BroadcastNotificationUserToGroupDTO(
        @NotNull
        @JsonProperty
        String messageBody,
        @NotNull
        @JsonProperty
        String messageHeader,
        @NotNull
        @JsonProperty
        long from,
        @NotNull
        @JsonProperty
        long group,
        @JsonProperty
        Map<String, Object> data
) {
}
