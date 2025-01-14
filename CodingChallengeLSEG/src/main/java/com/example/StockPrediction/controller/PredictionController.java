package com.example.StockPrediction.controller;

import com.example.StockPrediction.model.StockData;

import com.example.StockPrediction.service.CsvLoaderService;
import com.example.StockPrediction.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PredictionController {

    private final CsvLoaderService csvLoaderService;
    private final PredictionService predictionService;

    @Autowired
    public PredictionController(CsvLoaderService csvLoaderService, PredictionService predictionService) {
        this.csvLoaderService = csvLoaderService;
        this.predictionService = predictionService;

        this.csvLoaderService.loadCsvFiles();
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
