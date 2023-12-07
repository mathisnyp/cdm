package ie.tcd.cdm.backendforfrontend.controller;

import ie.tcd.cdm.backendforfrontend.dto.demo.MessageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8081/", "http://localhost:3000/, http://web-ui:3000/"})
@RequestMapping("/messages")
public class MessageController {

    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody MessageRequest request) {
        kafkaTemplate.send("testTopic", request.message());
    }

}
