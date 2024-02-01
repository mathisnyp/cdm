package ie.tcd.cdm.geo_service.model;

import lombok.Getter;

public class Event {
    @Getter
    String eventName;
    @Getter
    String eventType;
    @Getter
    String venue;
    @Getter
    Integer attendees;
    @Getter
    String date;

    public Event(String eventName, String eventType, String venue, Integer attendees, String date){
        this.eventName = eventName;
        this.eventType = eventType;
        this.venue = venue;
        this.attendees = attendees;
        this.date = date;
    }
}