package ie.tcd.cdm.geoservice;

import ie.tcd.cdm.geo_service.GeoServiceApplication;
import ie.tcd.cdm.geo_service.jpa.IntersectionRepository;
import ie.tcd.cdm.geo_service.jpa.RouteRepository;
import ie.tcd.cdm.geo_service.model.CdmPoint;
import ie.tcd.cdm.geo_service.model.Intersection;
import ie.tcd.cdm.geo_service.services.GeoCodingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Values;
import org.neo4j.driver.internal.value.PointValue;
import org.neo4j.driver.types.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataNeo4jTest
@ComponentScan(basePackageClasses = NodeTest.class)
@ContextConfiguration(classes = GeoServiceApplication.class)
@Disabled
class NodeTest {

    @Autowired
    private IntersectionRepository intersectionRepository;

    @Autowired
    private RouteRepository routeRepository;

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
    public void findIntersectionByAddressTest() {
        List<Intersection> result = intersectionRepository.findAllByAddressStreet("Dame Street");
        assertNotNull(result);
        for (Intersection intersection : result) {
            assertThat(intersection, instanceOf(Intersection.class));
        }
    }

    @Test
    public void findIntersectionByLocationTest() {
        Point point = Values.point(4326, -6.2620685, 53.3435867).asPoint();
        List<Intersection> result = intersectionRepository.findAllByAddressLocation(new PointValue(point));
        assertNotNull(result);
        for (Intersection intersection : result) {
            assertThat(intersection, instanceOf(Intersection.class));
        }

    }

    @Test
    public void findRouteTest() throws IOException {
        String destination = "Saint Andrew Street";
        Point origin = Values.point(4326, -6.2076991, 53.3173814).asPoint();
        var destinationCoordinates = this.geoCodingService.searchForPoint(destination);
        org.neo4j.driver.types.Point destinationPoint = Values.point(4326, destinationCoordinates.getLon(), destinationCoordinates.getLat()).asPoint();
        List<CdmPoint> expectedRoute = List.of(new CdmPoint(-6.2076944, 53.3171798), new CdmPoint(-6.2082799, 53.3173674), new CdmPoint(-6.2094255, 53.317706), new CdmPoint(-6.2096125, 53.3177905), new CdmPoint(-6.211547, 53.31838), new CdmPoint(-6.2124087, 53.3187192), new CdmPoint(-6.2151686, 53.3202873), new CdmPoint(-6.2154637, 53.3204578), new CdmPoint(-6.2159037, 53.3207012), new CdmPoint(-6.2170479, 53.3214692), new CdmPoint(-6.2179139, 53.3221382), new CdmPoint(-6.2209703, 53.3241814), new CdmPoint(-6.2215888, 53.3245329), new CdmPoint(-6.2243233, 53.3264411), new CdmPoint(-6.224416, 53.3265419), new CdmPoint(-6.2256066, 53.3274575), new CdmPoint(-6.2269364, 53.3278662), new CdmPoint(-6.2297551, 53.328635), new CdmPoint(-6.2300025, 53.3287117), new CdmPoint(-6.2302162, 53.328781), new CdmPoint(-6.2304974, 53.3288756), new CdmPoint(-6.2307643, 53.3289766), new CdmPoint(-6.2310198, 53.3290732), new CdmPoint(-6.2311177, 53.3291175), new CdmPoint(-6.2317565, 53.3294041), new CdmPoint(-6.232014, 53.3295282), new CdmPoint(-6.2322411, 53.3296419), new CdmPoint(-6.2327505, 53.3300276), new CdmPoint(-6.2349646, 53.3318179), new CdmPoint(-6.2353223, 53.3321897), new CdmPoint(-6.2354082, 53.3323049), new CdmPoint(-6.2354691, 53.3323976), new CdmPoint(-6.235815, 53.3328899), new CdmPoint(-6.2359843, 53.3331232), new CdmPoint(-6.2370802, 53.3347221), new CdmPoint(-6.238135, 53.3361423), new CdmPoint(-6.2400932, 53.3374225), new CdmPoint(-6.240171, 53.3374723), new CdmPoint(-6.2405988, 53.3376736), new CdmPoint(-6.24155, 53.3379888), new CdmPoint(-6.2422336, 53.3382153), new CdmPoint(-6.2423447, 53.3382521), new CdmPoint(-6.2432353, 53.3385472), new CdmPoint(-6.2433556, 53.338587), new CdmPoint(-6.2444031, 53.3389341), new CdmPoint(-6.2451496, 53.3391815), new CdmPoint(-6.2452282, 53.3392075), new CdmPoint(-6.2463552, 53.3395938), new CdmPoint(-6.2467104, 53.339727), new CdmPoint(-6.2507642, 53.3411734), new CdmPoint(-6.2525375, 53.3418519), new CdmPoint(-6.2545465, 53.3421878), new CdmPoint(-6.2563329, 53.3425769), new CdmPoint(-6.2578287, 53.3429228), new CdmPoint(-6.2594674, 53.3444768), new CdmPoint(-6.2599525, 53.3443284), new CdmPoint(-6.2608971, 53.3442807), new CdmPoint(-6.2608725, 53.3438602), new CdmPoint(-6.2617765, 53.3435433));
        List<CdmPoint> route = routeRepository.findRoute(new PointValue(origin), new PointValue(destinationPoint));
        System.out.println(route);
        assertEquals(expectedRoute, route);
    }

}
