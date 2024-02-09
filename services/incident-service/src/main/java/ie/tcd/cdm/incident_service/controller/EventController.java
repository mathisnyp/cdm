package ie.tcd.cdm.incident_service.controller;

import ie.tcd.cdm.incident_service.dto.CreateEventDTO;
import ie.tcd.cdm.incident_service.dto.UpdateEventDTO;
import ie.tcd.cdm.incident_service.model.Event;
import ie.tcd.cdm.incident_service.services.EventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public void createEvent(@RequestBody CreateEventDTO event) {
        eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public void updateEvent(@PathVariable long id, @RequestBody UpdateEventDTO event) {
        eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }


}
