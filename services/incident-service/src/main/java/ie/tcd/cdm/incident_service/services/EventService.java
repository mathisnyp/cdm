package ie.tcd.cdm.incident_service.services;

import ie.tcd.cdm.incident_service.dto.CreateEventDTO;
import ie.tcd.cdm.incident_service.dto.UpdateEventDTO;
import ie.tcd.cdm.incident_service.jpa.EventRepository;
import ie.tcd.cdm.incident_service.mapper.EventMapper;
import ie.tcd.cdm.incident_service.model.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper mapper;

    public void updateEvent(long id, UpdateEventDTO eventDTO) {
        Event event = this.getEventById(id);
        mapper.updateEventFromDTO(eventDTO, event);
        eventRepository.save(event);
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    public void createEvent(CreateEventDTO eventDTO) {
        eventRepository.save(Event.builder()
                        .eventName(eventDTO.eventName())
                        .eventType(eventDTO.eventType())
                        .venue(eventDTO.venue())
                        .attendees(eventDTO.attendees())
                        .generalDescription(eventDTO.generalDescription())
                        .eventTime(eventDTO.eventTime())
                .build());
    }

    public Event getEventById(long id) {
        return eventRepository.getReferenceById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}