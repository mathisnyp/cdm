package ie.tcd.cdm.communication_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@JsonDeserialize(builder = UpdateUserGroupDTO.UpdateUserGroupDTOBuilder.class)
@Builder
public record UpdateUserGroupDTO(

        @JsonProperty
        long userId,
        @JsonProperty
        long newGroupId
) {
}
