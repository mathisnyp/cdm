package ie.tcd.cdm.backendforfrontend.servicetests;

//import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GeoServiceTest {


    @BeforeAll
    public static void setUp() {
    }


    @Test
    public void GeoService() {
//        SettableListenableFuture<SendResult<String, String>> future = new SettableListenableFuture<>();
//        future.setException(new RuntimeException());
//
//        when(kafkaTemplate.send(any(ProducerRecord.class))).thenReturn(future);

    }
}