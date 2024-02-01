package ie.tcd.cdm.communication_service.create_incident;

import ie.tcd.cdm.geo_service.model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;

class CreateEventTest {
    void CreateEvent()
    {
        Assertions.assertDoesNotThrow(() -> {
        //var timestamp = new Timestamp(System.currentTimeMillis());
        Event event = new Event("Coachella", "Festival","Phoenix Park", 2000, "22.06.2024");

        Assertions.assertEquals("Coachella", event.getEventName());
        Assertions.assertEquals("Festival", event.getEventType());
        Assertions.assertEquals("Phoenix Park", event.getVenue());
        Assertions.assertEquals(2000, event.getAttendees());
        Assertions.assertEquals("22.06.2024", event.getDate());
    });
}
}