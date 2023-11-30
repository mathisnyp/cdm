package ie.tcd.cdm.backendforfrontend.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GeoService {
    private KafkaTemplate<String, String> kafkaTemplate;

    public GeoService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendHello() {
        kafkaTemplate.send("testTopic", "Hello world");
    }
}
