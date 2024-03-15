package ie.tcd.cdm.geo_service.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
abstract class KafkaEventsConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    protected String bootStrapServer;
}
