package ie.tcd.cdm.geo_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CdmPoint {
    private Double lon;
    private Double lat;
}
