package ie.tcd.cdm.communication_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

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
        long group
) {
}
