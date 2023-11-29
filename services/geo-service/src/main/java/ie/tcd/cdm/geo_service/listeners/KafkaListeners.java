package ie.tcd.cdm.geo_service.listeners;

import ie.tcd.cdm.geo_service.services.GeoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "testTopic", groupId = "test1")
    void listener(String data) {
        System.out.println(data);
        GeoService.TestConsume(data);
    }
}
