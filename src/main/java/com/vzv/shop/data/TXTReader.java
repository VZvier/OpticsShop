package com.vzv.shop.data;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TXTReader {

    private static final String PARAMS_URL = "src/main/resources/com/vzv/shop/parameters.txt";

    public static List<String> REGIONS;
    public static List<String> CITIES;
    public static Map<String, List<String>> STREETS;

    public static void readRegionsTxt() throws IOException {
        String url = "src/main/resources/com/vzv/shop/mapInfo/AdminCenters_JSON.txt";
        List<String> list = new ArrayList<>();
        Map<String, Map<String, String>> regionsFromJSON;
        try (Stream<String> stream = Files.lines(Path.of(url))){
            list = stream
                    .filter(s -> s.contains("\"@id\":") || s.contains("\"name:ru\":") || s.contains("\"name:uk\":"))
                    .map(String::strip)
                    .collect(Collectors.toSet()).stream().toList();
        } catch (Exception e){
            throw new NoSuchFileException("File at " + url + " not found!");
        }
        list.forEach(System.out::println);
    }

    public static String getParameter(String parameter) throws IOException {
        Map<String, String> map = new HashMap<>();
        try (Stream<String> stream = Files.lines(Path.of(PARAMS_URL))) {
            map = stream.map(str -> str.split(":")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
        }
        return map.get(parameter);
    }

    public static String setParameter(String parameter, String newValue) throws IOException {
        Map<String, String> paramsMap;
        try (Stream<String> stream = Files.lines(Path.of( PARAMS_URL ))) {
            paramsMap = stream.map(str -> str.split(":")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
        }
        paramsMap.computeIfPresent(parameter, (k,v) -> newValue);
        try (FileWriter writer = new FileWriter(PARAMS_URL)){
            for (Map.Entry<String, String> entry : paramsMap.entrySet()){
                writer.write(entry.getKey() + ":" + entry.getValue());
            }
        }
        return getParameter(parameter);
    }
}