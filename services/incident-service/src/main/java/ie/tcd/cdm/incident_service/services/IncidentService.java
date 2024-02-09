package ie.tcd.cdm.incident_service.services;

import ie.tcd.cdm.incident_service.dto.CreateIncidentDTO;
import ie.tcd.cdm.incident_service.dto.UpdateIncidentDTO;
import ie.tcd.cdm.incident_service.jpa.IncidentRepository;
import ie.tcd.cdm.incident_service.mapper.IncidentMapper;
import ie.tcd.cdm.incident_service.model.Incident;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final IncidentMapper mapper;

    public void updateIncident(long id, UpdateIncidentDTO incidentDTO) {
        Incident incident = this.getIncidentById(id);
        mapper.updateIncidentFromDTO(incidentDTO, incident);
        incidentRepository.save(incident);
    }

    public void deleteIncident(long id) {
        incidentRepository.deleteById(id);
    }

    public void createIncident(CreateIncidentDTO incidentDTO) {
        incidentRepository.save(Incident.builder()
                        .id(incidentDTO.id())
                        .reportIds(incidentDTO.reportIds())
                        .incidentName(incidentDTO.incidentName())
                .build());
    }

    public Incident getIncidentById(long id) {
        return incidentRepository.getReferenceById(id);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }
}