package com.example.StockPredictionTest.serviceTest;

import com.example.StockPrediction.model.StockData;
import com.example.StockPrediction.service.PredictionService;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPredictionService {

    private final PredictionService predictionService = new PredictionService();

    @Test
    public void testPredictNextValues() {
        List<StockData> dataPoints = List.of(
                new StockData("TSLA", "2023-12-01", 205.74),
                new StockData("TSLA", "2023-12-02", 206.15),
                new StockData("TSLA", "2023-12-03", 206.15)
        );

        List<Double> predictions = predictionService.predictNextValues(dataPoints);

        assertEquals(3, predictions.size());
        assertEquals(206.22, predictions.get(0));
    }
}