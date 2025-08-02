package com.vzv.shop.data;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class CSVFileReader {

    public static final Map<String, String> COUNTRIES = getCountriesEnRuNames(getCountriesFileContent());
    public static final Map<String, String> POPULATED_AREAS = getCountriesEnRuNames(getCountriesFileContent());

    public static List<String> getCountriesFileContent() {
        String url = "src/main/resources/com/vzv/shop/countries.csv";
        return getStrings(url);
    }

    public static List<String> getPopulatedAreasFileContent() {
        String url = "com/vzv/shop/CitiesAndVillages-14_March.csv";
        return getStrings(url);
    }

    @NotNull
    private static List<String> getStrings(String url) {
        List<String> result = new LinkedList<>();
        try (Stream<String> lines = Files.lines(Path.of(url))) {
            result = lines.collect(Collectors.toCollection(LinkedList::new));
        } catch (IOException e) {
            log.error("File: {} not found! \n {}", url, e.getMessage());
        }
        return result;
    }

    public static Map<String, String> getCountriesEnRuNames(List<String> allLangNames){
        List<String> line = Arrays.asList(allLangNames.get(0).split(","));
        int enNamePositions = line.indexOf("en");
        int ruNamePositions = line.indexOf("ru");
        Map<String, String> result = new LinkedHashMap<>(allLangNames.size() - 1);
        for(int i = 1; i < allLangNames.size(); i++){
            line = Arrays.asList(allLangNames.get(i).split(",(?=\\S)") );
            result.put(line.get(enNamePositions).replace("\"", ""),
                    line.get(ruNamePositions).replace("\"", ""));
        }
        return result;
    }

    public static Map<String, String> getPopulatedAreas(List<String> fileAsLines){
        List<String> lineParts = Arrays.asList(fileAsLines.get(0).split(",")); // length: 9

        return null;
    }

    public static Map<String, String> getEnRuCountries(){
        return COUNTRIES;
    }

    public class Settlement{
        String region;
        String type;
        String comunity;
        String name;
    }
}
