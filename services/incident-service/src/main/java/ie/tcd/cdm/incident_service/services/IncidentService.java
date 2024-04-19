package ie.tcd.cdm.incident_service.services;

import ie.tcd.cdm.incident_service.apis.communication.model.BroadcastNotificationFromUserDTO;
import ie.tcd.cdm.incident_service.apis.geo.GeoService;
import ie.tcd.cdm.incident_service.apis.geo.model.UpdateWeightDTO;
import ie.tcd.cdm.incident_service.dto.CreateIncidentDTO;
import ie.tcd.cdm.incident_service.dto.UpdateIncidentDTO;
import ie.tcd.cdm.incident_service.jpa.IncidentRepository;
import ie.tcd.cdm.incident_service.mapper.IncidentMapper;
import ie.tcd.cdm.incident_service.model.Incident;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ie.tcd.cdm.incident_service.apis.communication.CommunicationService;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final IncidentMapper mapper;
    private final GeoService geoService;
    private final CommunicationService communicationService;

    public void updateIncident(long id, UpdateIncidentDTO incidentDTO) {
        Incident incident = this.getIncidentById(id);
        mapper.updateIncidentFromDTO(incidentDTO, incident);
        incidentRepository.save(incident);
    }

    public void deleteIncident(long id) {
        incidentRepository.deleteById(id);
    }

    public void createIncident(CreateIncidentDTO incidentDTO) throws IOException, InterruptedException {
        incidentRepository.save(Incident.builder()
                .reportIds(incidentDTO.reportIds())
                .incidentName(incidentDTO.incidentName())
                .build());

        String str  = String.format("A dangerous event has happened on %s. Take care.", incidentDTO.incidentName());

        BroadcastNotificationFromUserDTO broadcastNotificationFromUserDTO = new BroadcastNotificationFromUserDTO(
                str,
                "!!!WARNING!!!",
                0,
                null
        );
        communicationService.broadcastNotification(broadcastNotificationFromUserDTO);
    }

    public Incident getIncidentById(long id) {
        return incidentRepository.getReferenceById(id);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public void checkValidIncidents() throws IOException, InterruptedException {
        List<Incident>allIncidents = getAllIncidents();
        LocalDateTime currentTime = LocalDateTime.now();
        for(Incident incident: allIncidents) {
            LocalDateTime createdAt = incident.getCreatedAt();
            LocalDateTime updatedAt = incident.getUpdatedAt();
//            LocalDateTime currentTimeLess1Hour = currentTime.minusHours(1);
            LocalDateTime currentTimeLess2Minutes = currentTime.minusMinutes(1);


//            Timestamp oneHourAgoTimestamp = Timestamp.valueOf(currentTimeLess1Hour);
            Timestamp twoMinutesAgoTimestamp = Timestamp.valueOf(currentTimeLess2Minutes);
            Timestamp createdAtTimeStamp = Timestamp.valueOf(createdAt);
            Timestamp updatedAtTimeStamp = Timestamp.valueOf(updatedAt);

//            int compareCreatedAt = oneHourAgoTimestamp.compareTo(createdAtTimeStamp);
//            int compareUpdatedAt = oneHourAgoTimestamp.compareTo(updatedAtTimeStamp);

            int compareCreatedAt = twoMinutesAgoTimestamp.compareTo(createdAtTimeStamp);
            int compareUpdatedAt = twoMinutesAgoTimestamp.compareTo(updatedAtTimeStamp);

            // if there are any invalid ones call deleteIncident(id) needing the id from step above
            if(compareCreatedAt > 0 && compareUpdatedAt > 0) {
                String incidentName = incident.getIncidentName();
                deleteIncident(incident.getId());
                UpdateWeightDTO updateWeightDTO = new UpdateWeightDTO(incidentName);
                geoService.deleteIncident(updateWeightDTO);
            }
        }
    }
}