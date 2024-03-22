package ie.tcd.cdm.geo_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Node("Links")

public class Links {
    @Id
    private String osmid;

    @Property("u")
    private String u;

    @Property("v")
    private String v;

    @Property("key")
    private int key;

    @Property("oneway")
    private boolean oneway;

    @Property("lanes")
    private List<String> lanes;

    @Property("name")
    private String name;

    @Property("highway")
    private String highway;

    @Property("maxspeed")
    private List<String> maxspeed;

    @Property("reversed")
    private boolean reversed;

    @Property("length")
    private double length;

    @Property("ref")
    private String ref;

    @Property("geometry")
    private String geometry;

    @Property("access")
    private String access;

    @Property("tunnel")
    private boolean tunnel;

    @Property("bridge")
    private boolean bridge;

    @Property("width")
    private double width;

    @Property("junction")
    private String junction;

    @Relationship(type = "ROAD_SEGMENT", direction = Relationship.Direction.OUTGOING)
    private Intersection startIntersection;

    @Relationship(type = "ROAD_SEGMENT", direction = Relationship.Direction.INCOMING)
    private Intersection endIntersection;

}
