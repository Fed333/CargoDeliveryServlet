package com.epam.cargo.utils;

import com.epam.cargo.entity.WeightFare;

import java.util.List;

public class CsvFileWeightFareReader {

    public static List<WeightFare> readWeightFaresCsv(String filename){
        return CsvFileReader.readCsv(filename, (String[] p) ->
                new WeightFare(
                        Long.parseLong(p[0]),
                        Integer.parseInt(p[2]),
                        Integer.parseInt(p[3]),
                        Double.parseDouble(p[1])
                )
        );
    }
}
