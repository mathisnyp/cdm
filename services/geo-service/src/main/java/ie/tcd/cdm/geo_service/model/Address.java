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
@Node("Address")
public class Address {
    @Id
    private String osmid;

    @Property("location")
    private PointValue location;

    @Property("address_number")
    private String addressNumber;

    @Property("address_postcode")
    private String addressPostcode;

    @Property("address_street")
    private String addressStreet;

    @Property("geom_type")
    private String geomType;

    @Property("latitude")
    private Double latitude;

    @Property("longitude")
    private Double longitude;

    @Property("full_address")
    private String fullAddress;

    @Relationship(type = "NEAREST_INTERSECTION", direction = Relationship.Direction.OUTGOING)
    private Intersection nearestIntersection;
}

