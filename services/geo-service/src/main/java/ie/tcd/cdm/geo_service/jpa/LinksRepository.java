package ie.tcd.cdm.geo_service.jpa;
import ie.tcd.cdm.geo_service.model.Links;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LinksRepository extends Neo4jRepository<Links, String>{
    Links findOneByOsmid(String osmid);
}
