package org.winside.kata.population.density;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GridTest {
    static List<PointOfInterest> POIs = List.of(
            new PointOfInterest(-48.6, -37.7),
            new PointOfInterest(-2.1, 38.1),
            new PointOfInterest(0.1, -0.1),
            new PointOfInterest(-2.5, 38.3),
            new PointOfInterest(6.8, -6.9),
            new PointOfInterest(6.6, -6.9),
            new PointOfInterest(-27.1, 8.4),
            new PointOfInterest(-2.3, 38.3));
    private final List<Zone> zoneList = new ArrayList<>();
    private final Grid grid = new Grid(zoneList);

    @Test
    void shouldGridByFull() {
        // WHEN
        grid.populateZoneList();

        // THEN
        Assertions.assertEquals((180 * 4 * 90 * 4), zoneList.size());
    }

    @Test
    void shouldReturnTheRightNumberOfPOIs() {
        // GIVEN
        Zone zone = new Zone(6.5, 7, -7, -6.5);

        // WHEN
        int number = grid.getTheRightNumberOfPOIsInGivenZone(zone, POIs);

        // THEN
        Assertions.assertEquals(2, number);
    }

    @Test
    void shouldGetTheDensestZoneGivenTheRightPOIs () {

        // GIVEN
        grid.populateZoneList();

        // WHEN
        List<Zone> zones = grid.getTheDensestZones(POIs);

        // THEN
        Assertions.assertTrue(zones.contains(new Zone(-2.5, -2, 38, 38.5)));
        Assertions.assertTrue(zones.contains(new Zone(6.5, 7, -7, -6.5)));
        Assertions.assertEquals(2, zones.size());

    }
}
