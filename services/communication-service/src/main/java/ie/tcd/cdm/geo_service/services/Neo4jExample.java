package ie.tcd.cdm.geo_service.services;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Service;

import static org.neo4j.driver.Values.parameters;

@Service
public class Neo4jExample {

    public static void createNode(String uri, String user, String password, String message) {
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))) {
            try (Session session = driver.session()) {
                String queryCreateNode = "CREATE (e1:Example {message: $message})";
                session.run(queryCreateNode, parameters("message", message));
                System.out.println("Sent to Neo4j");
            }
        }
    }

    public static String readNode(String uri, String user, String password) {
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))) {
            try (Session session = driver.session()) {
                String queryReadNode = "MATCH (e1:Example) RETURN e1.message AS message";
                Result result = session.run(queryReadNode);
                Record record = result.next();
                String final_message = record.get("message").asString();
                return final_message;

            }
        }
    }
}
