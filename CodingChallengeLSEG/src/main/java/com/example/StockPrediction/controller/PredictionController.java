package com.example.StockPrediction.controller;

import com.example.StockPrediction.model.StockData;

import com.example.StockPrediction.service.CsvLoaderService;
import com.example.StockPrediction.service.PredictionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PredictionController {

    private final CsvLoaderService csvLoaderService;
    private final PredictionService predictionService;

    @PostConstruct
    private void initialize() {
        csvLoaderService.loadCsvFiles();
    }


    @GetMapping("/predict")
    public Map<String, Object> predict(@RequestParam String filename) {
        List<StockData> dataPoints = csvLoaderService.getRandomDataPoints(filename);
        List<Double> predictions = predictionService.predictNextValues(dataPoints);

        return Map.of(
                "dataPoints", dataPoints,
                "predictions", predictions
        );
    }
}
