package com.epam.cargo.service;

import com.epam.cargo.algorithms.Dijkstra;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utils class with common logic for working with cities.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class CityUtils {

    /**
     * Calculate smallest distance between cityFrom and cityTo.
     * @param cityFrom localized source city
     * @param cityTo localized destination city
     * @param directions list of all available directions between cities
     * @return number which represents smallest distance between cityFrom and cityTo
     * if no way exists return Double.POSITIVE_INFINITY
     * */
    public static Double getMinDistance(City cityFrom, City cityTo, List<DirectionDelivery> directions){

        Set<City> cities = directions.stream().collect(getCityFromDirectionsCollector());
        Map<City, Integer> cityIntegerMap = new HashMap<>();

        int index = 0;
        for (City city:cities) {
            cityIntegerMap.put(city, index);
            ++index;
        }

        double[][] graph = createGraph(directions, cityIntegerMap);

        Integer source = cityIntegerMap.get(cityFrom);
        Integer dest = cityIntegerMap.get(cityTo);

        Dijkstra dijkstra = new Dijkstra(graph, source);
        return calculateMinDistance(dest, dijkstra);
    }

    /**
     * Calculate smallest distance between cityFrom and cityTo.
     * @param cityFrom localized source city
     * @param cityTo localized destination city
     * @param directions list of all available directions between cities
     * @return City.Distance object with source city, destination city, smallest distance (rounded number to 1 decimal place)
     * and route if route doesn't exist return null
     * */
    public static City.Distance getDistance(City cityFrom, City cityTo, List<DirectionDelivery> directions){

        Set<City> cities = directions.stream().collect(getCityFromDirectionsCollector());

        Map<City, Integer> cityIntegerMap = new HashMap<>();
        Map<Integer, City> integerCityMap = new HashMap<>();

        int index = 0;
        for (City city:cities) {
            cityIntegerMap.put(city, index);
            integerCityMap.put(index, city);
            ++index;
        }

        double[][] graph = createGraph(directions, cityIntegerMap);

        Integer source = cityIntegerMap.get(cityFrom);
        Integer dest = cityIntegerMap.get(cityTo);

        Dijkstra dijkstra = new Dijkstra(graph, source);
        double minDistance = calculateMinDistance(dest, dijkstra);

        if (minDistance != Double.POSITIVE_INFINITY){
            List<City> route = buildCitiesSmallestRout(dijkstra.buildShortestRoute(dest), integerCityMap);
            return new City.Distance(cityFrom, cityTo, minDistance, route);
        }

        return null;
    }

    private static double calculateMinDistance(Integer dest, Dijkstra dijkstra) {
        return Math.round(dijkstra.calculateMinDistance(dest) * 10.0) / 10.0;
    }

    private static double[][] createGraph(List<DirectionDelivery> directions, Map<City, Integer> cityIntegerMap) {
        int size = cityIntegerMap.size();
        double[][] graph = new double[size][size];

        for (int i = 0; i < graph.length; i++) {
            Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
        }
        for (DirectionDelivery d : directions) {
            int i = cityIntegerMap.get(d.getSenderCity());
            int j = cityIntegerMap.get(d.getReceiverCity());
            graph[i][j] = graph[j][i] = d.getDistance();
        }
        return graph;
    }

    private static List<City> buildCitiesSmallestRout(List<Integer> routAsInt, Map<Integer, City> integerCityMap){
        return routAsInt.stream().map(integerCityMap::get).collect(Collectors.toList());
    }

    private static Collector<DirectionDelivery, HashSet<City>, HashSet<City>> getCityFromDirectionsCollector() {
        return Collector.of(
                (Supplier<HashSet<City>>) HashSet::new,
                (set, d) -> {
                    set.add(d.getSenderCity());
                    set.add(d.getReceiverCity());
                }, (cities1, cities2) -> {
                    cities1.addAll(cities2);
                    return cities1;
                }
        );
    }

}
