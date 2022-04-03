package com.epam.cargo.utils;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CsvFilesDirectionDeliveryReader {

    public static void readDirectionsDeliveryCsv(String fileName, Map<Long, City> citiesById, List<DirectionDelivery> directions){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line = "";
            while(!Objects.isNull(line = br.readLine())){
                String[] params = line.split(",");
                DirectionDelivery direction = parseDirection(params, citiesById);
                directions.add(direction);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DirectionDelivery parseDirection(String[] params, Map<Long, City> citiesById){
        Long id = Long.parseLong(params[0]);
        Long senderCityId = Long.parseLong(params[1]);
        Long receiverCityId = Long.parseLong(params[2]);
        Double distance = Double.parseDouble(params[3]);

        City senderCity = citiesById.get(senderCityId);
        City receiverCity = citiesById.get(receiverCityId);
        return new DirectionDelivery(id, senderCity, receiverCity, distance);
    }
}
