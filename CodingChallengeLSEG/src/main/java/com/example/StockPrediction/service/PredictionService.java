package com.example.StockPrediction.service;

import com.example.StockPrediction.model.StockData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    public List<Double> predictNextValues(List<StockData> dataPoints) {
        List<Double> prices = new ArrayList<>(
                dataPoints.stream()
                        .map(StockData::getPrice)
                        .toList()
        );
        double trend = calculateTrend(prices);

        List<Double> predictions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            double average = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double predictedValue = average + (trend * (i + 1));
            predictedValue = Math.round(predictedValue * 100.0) / 100.0; // rounding to 2 decimals
            predictions.add(predictedValue);
            prices.add(predictedValue);
        }

        return predictions;
    }

    private double calculateTrend(List<Double> prices) {
        double totalChange = 0.0;
        for (int i = 1; i < prices.size(); i++) {
            totalChange += prices.get(i) - prices.get(i - 1);

        }
        return totalChange / (prices.size() - 1);
    }
}