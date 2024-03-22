package ie.tcd.cdm.geo_service.jpa;

import ie.tcd.cdm.geo_service.model.Address;
import org.neo4j.driver.internal.value.PointValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AddressRepository extends Neo4jRepository<Address, String> {
    Address findAddressByOsmid(String osmid);
    Address findAddressByLocation(PointValue location);
    Address findAddressByFullAddress(String full_address);
}
