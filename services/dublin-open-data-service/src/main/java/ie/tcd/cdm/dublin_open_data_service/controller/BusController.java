package ie.tcd.cdm.dublin_open_data_service.controller;

import ie.tcd.cdm.dublin_open_data_service.services.DublinOpenDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus")
public class BusController {

    private final DublinOpenDataService dublinOpenDataService;

    public BusController(DublinOpenDataService dublinOpenDataService) {
        this.dublinOpenDataService = dublinOpenDataService;
    }

    @GetMapping("/")
    public String getBusData() {
        return dublinOpenDataService.getAllBuses();
    }
}