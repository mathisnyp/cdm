package ie.tcd.cdm.communication_service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        long from


) {

}
