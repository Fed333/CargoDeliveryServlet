package com.epam.cargo.utils;

import com.epam.cargo.entity.DimensionsFare;

import java.util.List;

public class CsvFileDimensionsFareReader {

    public static List<DimensionsFare> readDimensionsFaresCsv(String filename){
        return CsvFileReader.readCsv(filename,  (String[] parameters) -> new DimensionsFare(
                        Long.parseLong(parameters[0]),
                        Integer.parseInt(parameters[1]),
                        Integer.parseInt(parameters[2]),
                        Double.parseDouble(parameters[3])
                )
        );
    }
}
