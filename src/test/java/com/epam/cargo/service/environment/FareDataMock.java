package com.epam.cargo.service.environment;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FareDataMock<T> {

    private List<T> fares;

    public FareDataMock(List<T> fares) {
        this.fares = fares;
    }

    public T fareValueBetweenBy(Integer value, Function<T, Integer> from, Function<T, Integer> to){
        List<T> accordingFare = fares.stream().filter(f -> isValueBetween(value, from.apply(f), to.apply(f))).collect(Collectors.toList());

        if (accordingFare.isEmpty()){
            return null;
        }

        if (accordingFare.size() != 1){
            throw new IllegalStateException("Bad test cases environment");
        }

        return accordingFare.get(0);
    }

    public T maxFare(Function<T, Double> by){
        return fares.stream().max(Comparator.comparing(by)).orElseThrow(()->new IllegalStateException("No max fares found!"));
    }

    private static boolean isValueBetween(Integer  weight, Integer from, Integer to){
        return weight >= from && weight <= to;
    }

}
