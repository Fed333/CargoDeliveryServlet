package com.epam.cargo.entity;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Represents city
 * Used in addresses and delivery distances
 * Implements Entity, Cloneable interfaces
 * */
public class City implements Entity<Long>, Cloneable{

    private Long id;

    private String name;

    private String zipcode;

    public City() {
    }

    public City(String name, String zipcode) {
      this(null, name, zipcode);
    }

    public City(Long id, String name, String zipcode) {
        this.id = id;
        this.name = name;
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id.equals(city.id) &&
                name.equals(city.name) &&
                zipcode.equals(city.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, zipcode);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }

    public static class NameComparator implements Comparator<City> {

        private final Collator collator;

        public NameComparator(Collator collator) {
            this.collator = collator;
        }

        @Override
        public int compare(City o1, City o2) {
            return collator.compare(o1.getName(), o2.getName());
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public static class Distance{

        private City cityFrom;
        private City cityTo;

        private Double distance;

        private List<City> route;

        public Distance(City cityFrom, City cityTo, Double distance, List<City> route) {
            this.cityFrom = cityFrom;
            this.cityTo = cityTo;
            this.distance = distance;
            this.route = route;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Distance)) return false;
            Distance distance1 = (Distance) o;
            return Objects.equals(cityFrom, distance1.cityFrom) &&
                    Objects.equals(cityTo, distance1.cityTo) &&
                    Objects.equals(distance, distance1.distance) &&
                    Objects.equals(route, distance1.route);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cityFrom, cityTo, distance, route);
        }

        public City getCityFrom() {
            return cityFrom;
        }

        public City getCityTo() {
            return cityTo;
        }

        public Double getDistance() {
            return distance;
        }

        public List<City> getRoute() {
            return route;
        }
    }
}
