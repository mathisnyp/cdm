package ie.tcd.cdm.incident_service.controller;

import ie.tcd.cdm.incident_service.dto.CreateIncidentDTO;
import ie.tcd.cdm.incident_service.dto.UpdateIncidentDTO;
import ie.tcd.cdm.incident_service.model.Incident;
import ie.tcd.cdm.incident_service.services.IncidentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incident")
public class IncidentController {
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
    
    @GetMapping("/{id}")
    public Incident getIncident(@PathVariable long id) {
        return incidentService.getIncidentById(id);
    }

    @GetMapping("/")
    public List<Incident> getIncidents() { return incidentService.getAllIncidents(); }

    @PostMapping
    public void createIncident(@RequestBody CreateIncidentDTO incident) {
        incidentService.createIncident(incident);
    }

    @PutMapping("/{id}")
    public void updateIncident(@PathVariable long id, @RequestBody UpdateIncidentDTO incident) {
        incidentService.updateIncident(id, incident);
    }

    @DeleteMapping("/{id}")
    public void deleteIncident(@PathVariable long id) {
        incidentService.deleteIncident(id);
    }


}