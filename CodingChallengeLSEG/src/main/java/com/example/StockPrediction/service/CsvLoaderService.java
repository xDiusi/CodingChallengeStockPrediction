package com.example.StockPrediction.service;

import com.example.StockPrediction.model.StockData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service @Slf4j
public class CsvLoaderService {

    @Value("classpath:CSVTemplates/**/*.csv")
    private Resource[] csvResources;

    private final Map<String, List<StockData>> stockDataByFile = new HashMap<>();

    public void loadCsvFiles() {
        for (Resource resource : csvResources) {
            try(
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);)
            {
                List<StockData> data = bufferedReader.lines()
                        .map(line -> {
                            String[] parts = line.split(",");
                            return new StockData(parts[0], parts[1], Double.parseDouble(parts[2]));
                        })
                        .collect(Collectors.toList());
                stockDataByFile.put(resource.getFilename(), data);
            } catch (Exception e) {
                log.error("Failed to load CSV file: {}", resource.getFilename(), e);
            }
        }
    }


    public List<StockData> getRandomDataPoints(String filename)
    {
        List<StockData> data = stockDataByFile.get(filename);
        if(data == null || data.size() < 10 )
        {
            throw new IllegalArgumentException("Not enough data points found for filename: " + filename);
        }

        Random random = new Random();
        int startIndex = random.nextInt(data.size()- 9);
        return data.subList(startIndex, startIndex+10);
    }
}

