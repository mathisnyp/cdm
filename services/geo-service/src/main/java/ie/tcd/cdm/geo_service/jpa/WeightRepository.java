package ie.tcd.cdm.geo_service.jpa;

import ie.tcd.cdm.geo_service.model.Route;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface WeightRepository extends Neo4jRepository<Route, Long> {

    @Query("MATCH (to:Address {address_street:$addressStreet})-[:NEAREST_INTERSECTION]->(source:Intersection)-[r:ROAD_SEGMENT]->() SET r.length = r.length + 10000")
    void addWeight(String addressStreet);

    @Query("MATCH (to:Address {address_street:$addressStreet})-[:NEAREST_INTERSECTION]->(source:Intersection)-[r:ROAD_SEGMENT]->() SET r.length = r.length - 10000 ")
    void removeWeight(String addressStreet);

}
