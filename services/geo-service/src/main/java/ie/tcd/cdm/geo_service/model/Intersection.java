package ie.tcd.cdm.geo_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.driver.internal.value.PointValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Node("Intersection")
public class Intersection {

    @Id
    private int osmid;

    @Property("location")
    private PointValue location;

    @Property("ref")
    private Object ref;

    @Property("highway")
    private Object highway;

    @Property("streetCount")
    private int streetCount;

    @Relationship(type = "ROAD_SEGMENT", direction = Relationship.Direction.INCOMING)
    private Links incomingLink;

    @Relationship(type = "ROAD_SEGMENT", direction = Relationship.Direction.OUTGOING)
    private Links outgoingLink;

    @Relationship(type = "NEAREST_INTERSECTION", direction = Relationship.Direction.INCOMING)
    private Address nearestAddress;
}
