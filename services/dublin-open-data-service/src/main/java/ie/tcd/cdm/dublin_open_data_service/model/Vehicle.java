package ie.tcd.cdm.dublin_open_data_service.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    @Setter
    @Getter
    private Trip trip;
    @Setter
    @Getter
    private Position position;
    @Setter
    @Getter
    private Timestamp timestamp;
    @Setter
    @Getter
    private String vehicleId;
}