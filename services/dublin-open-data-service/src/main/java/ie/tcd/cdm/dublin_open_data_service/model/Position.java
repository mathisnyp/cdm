package ie.tcd.cdm.dublin_open_data_service.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Position {
    @Setter
    @Getter
    String latitude;
    @Setter
    @Getter
    String longitude;
}
