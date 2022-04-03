package com.epam.cargo.utils;

import com.epam.cargo.entity.DistanceFare;

import java.util.List;

public class CsvFilesDistanceFareReader {

    public static List<DistanceFare> readDistanceFaresCsv(String fileName){
        return CsvFileReader.readCsv(fileName, CsvFilesDistanceFareReader::parseDistanceFare);
    }

    private static DistanceFare parseDistanceFare(String[] params){
        Long id = Long.parseLong(params[0]);
        Integer from = Integer.parseInt(params[1]);
        Integer to = Integer.parseInt(params[2]);
        Double price = Double.parseDouble(params[3]);
        return new DistanceFare(id, from, to, price);
    }
}
