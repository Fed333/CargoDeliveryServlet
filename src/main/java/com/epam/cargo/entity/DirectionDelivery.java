package com.epam.cargo.entity;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents directions of delivery
 * Used in finding delivery route
 * */
public class DirectionDelivery implements Cloneable{

    private Long id;

    private City senderCity;

    private City receiverCity;

    private Double distance;

    public DirectionDelivery() {
    }

    public DirectionDelivery(City senderCity, City receiverCity, Double distance) {
       this(null, senderCity, receiverCity, distance);
    }

    public DirectionDelivery(Long id, City senderCity, City receiverCity, Double distance) {
        this.id = id;
        this.senderCity = senderCity;
        this.receiverCity = receiverCity;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectionDelivery)) return false;
        DirectionDelivery that = (DirectionDelivery) o;
        return id.equals(that.id) &&
                senderCity.equals(that.senderCity) &&
                receiverCity.equals(that.receiverCity) &&
                distance.equals(that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderCity, receiverCity, distance);
    }

    @Override
    public String toString() {
        return "DirectionDelivery{" +
                "id=" + id +
                ", senderCity=" + senderCity +
                ", receiverCity=" + receiverCity +
                ", distance=" + distance +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(City senderCity) {
        this.senderCity = senderCity;
    }

    public City getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(City receiverCity) {
        this.receiverCity = receiverCity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DirectionDelivery clone = (DirectionDelivery) super.clone();
        clone.senderCity = (City) this.senderCity.clone();
        clone.receiverCity = (City) this.receiverCity.clone();
        return clone;
    }

    public static class SenderCityNameComparator implements Comparator<DirectionDelivery> {

        private final Collator collator;

        public SenderCityNameComparator(Collator collator) {
            this.collator = collator;
        }

        @Override
        public int compare(DirectionDelivery o1, DirectionDelivery o2) {
            return new City.NameComparator(collator).compare(o1.getSenderCity(), o2.getSenderCity());
        }
    }

    public static class ReceiverCityNameComparator implements Comparator<DirectionDelivery> {

        private final Collator collator;

        public ReceiverCityNameComparator(Collator collator) {
            this.collator = collator;
        }

        @Override
        public int compare(DirectionDelivery o1, DirectionDelivery o2) {
            return new City.NameComparator(collator).compare(o1.getReceiverCity(), o2.getReceiverCity());
        }
    }

    public static class DistanceComparator implements Comparator<DirectionDelivery> {
        @Override
        public int compare(DirectionDelivery o1, DirectionDelivery o2) {
            return o1.getDistance().compareTo(o2.getDistance());
        }
    }
}
