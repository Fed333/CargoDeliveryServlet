package com.epam.cargo.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileReader {

    /**
     * Reads Csv file, with boxing parsed values to List
     * @param fileName name of Csv file
     * @param parser parametrized parser, which comply with corresponding Csv file format
     * @return list of objects generated by given parser, if no objects where found return Collections.emptyList()
     * */
    public static <T> List<T> readCsv(String fileName, ParseObjectFromCsv<T> parser){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            return reader.lines()
                    .map(l -> l.split(","))
                    .map(parser::parseCsv).
                    collect(Collectors.toList());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @FunctionalInterface
    public interface ParseObjectFromCsv <T> {
        T parseCsv(String[] params);
    }
}
