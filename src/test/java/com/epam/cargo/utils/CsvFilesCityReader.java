package com.epam.cargo.utils;

import com.epam.cargo.entity.City;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CsvFilesCityReader {

    public static void readCitiesCsv(String fileName, Map<Long, City> citiesById, Map<String, City> citiesByZipcode){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line = "";
            while(!Objects.isNull(line = br.readLine())){
                String[] params = line.split(",");
                City city = parseCity(params);
                citiesById.put(city.getId(), city);
                citiesByZipcode.put(city.getZipcode(), city);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static City parseCity(String[] params){
        Long id = Long.parseLong(params[0]);
        String name = params[1];
        String zipcode = params[2];
        return new City(id, name, zipcode);
    }

}
