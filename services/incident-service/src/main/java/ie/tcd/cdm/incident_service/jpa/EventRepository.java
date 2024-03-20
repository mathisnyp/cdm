package ie.tcd.cdm.incident_service.jpa;

import ie.tcd.cdm.incident_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}