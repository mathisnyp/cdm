package ie.tcd.cdm.geo_service.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route {
    @GeneratedValue
    @Id
    @Setter
    @Getter
    private long id;

    private List<CdmPoint> points;
}
