package ie.tcd.cdm.incident_service.configs;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${incident.topic.name}")
    private String incidentTopicName;

    @Value("${incident.topic.partitions}")
    private int incidentTopicPartitions;

    @Value("${incident.topic.replication}")
    private short incidentTopicReplication;

    private AdminClient adminClient;

    @Bean
    public void createTopics() throws ExecutionException, InterruptedException {
        Properties config = new Properties();
        config.put("bootstrap.servers", bootstrapAddress);

        adminClient = AdminClient.create(config);

        createTopicIfNotExists(incidentTopicName, incidentTopicPartitions, incidentTopicReplication);
    }

    private void createTopicIfNotExists(String topicName, int numPartitions, short replicationFactor) {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        try {
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
        } catch (InterruptedException | ExecutionException e) {
            if (!(e.getCause() instanceof TopicExistsException)) {
                throw new RuntimeException("Failed to create topic: " + topicName, e);
            }
            // Topic already exists - this is fine if we're not attempting to update settings for an existing topic
        }
    }
}

