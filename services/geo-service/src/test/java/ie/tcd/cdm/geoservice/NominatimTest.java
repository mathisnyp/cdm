package ie.tcd.cdm.geoservice;

import ie.tcd.cdm.geo_service.model.CdmPoint;
import ie.tcd.cdm.geo_service.services.GeoCodingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class NominatimTest {
    private GeoCodingService geoCodingService;

    @BeforeEach
    void prepareTest() {
        this.geoCodingService = new GeoCodingService();
    }

    @AfterEach
    void cleanUp() {
        this.geoCodingService = null;
    }


    @Test
    void searchForCoordinatesOutsideIrelandTest() throws IOException {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            var geoPoint = this.geoCodingService.searchForPoint(
                    "Villingen-Schwenningen"
            );
        });
    }

    @Test
    void searchForCoordinatesOutsideDublinTest() throws IOException {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            var geoPoint = this.geoCodingService.searchForPoint(
                    "The SpitJack Cork"
            );
        });
    }

    @Test
    void searchForCoordinatesTest() throws IOException {
        var geoPoint = this.geoCodingService.searchForPoint(
                "GPO"
        );
        Assertions.assertEquals(new CdmPoint(-6.2617235, 53.3495059), geoPoint);
    }
}
