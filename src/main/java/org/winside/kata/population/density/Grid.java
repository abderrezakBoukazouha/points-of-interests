package org.winside.kata.population.density;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Grid {

    private final List<Zone> zoneList;

    private final double minLongitude = -180;
    private final double maxLongitude = 180;
    private final double minLatitude = -90;
    private final double maxLatitude = 90;

    public Grid(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public List<Zone> getZoneList() {
        return zoneList;
    }


    public void populateZoneList() {
        for (double longitude = minLongitude; longitude < maxLongitude; longitude += 0.5) {

            for (double latitude = minLatitude; latitude < maxLatitude; latitude += 0.5) {
                zoneList.add(new Zone(latitude, latitude + 0.5, longitude, longitude + 0.5));
            }
        }
    }

    public int getTheRightNumberOfPOIsInGivenZone(Zone zone, List<PointOfInterest> poIs) {
        int numberOfPoi = 0;
        for (PointOfInterest poi : poIs) {
            if (poiIsInsideZone(poi, zone)) {
                numberOfPoi++;
            }
        }

        return numberOfPoi;
    }

    private boolean poiIsInsideZone(PointOfInterest poi, Zone zone) {
        return zone.max_latitude() > poi.latitude() &&
                zone.min_latitude() < poi.latitude() &&
                zone.max_longitude() > poi.longitude() &&
                zone.min_longitude() < poi.longitude();
    }


    public static List<Zone> getTheDensestZones(List<PointOfInterest> listPoi) {
        List<Zone> poiZones = new ArrayList<>();
        for (Zone zone : zoneList) {
            for (PointOfInterest poi : listPoi) {
                if (poiIsInsideZone(poi, zone)) {
                    poiZones.add(zone);
                }
            }
        }


        Map<Zone,Long> collect = poiZones.stream()
                .collect( Collectors.groupingBy( Function.identity(), Collectors.counting()));

        collect.values().stream().filter(zone -> zone.value == Collections.max(collect.values())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return collect.keySet().stream().toList();

    }
}
