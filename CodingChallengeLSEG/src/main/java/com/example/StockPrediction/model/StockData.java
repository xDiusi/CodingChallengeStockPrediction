package com.example.StockPrediction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockData {
    private String stockExchangeName;
    private String timestamp;
    private double price;
}
