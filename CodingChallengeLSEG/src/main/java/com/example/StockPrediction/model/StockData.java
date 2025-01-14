package com.example.StockPrediction.model;

public class StockData {
    private String stockExchangeName;
    private String timestamp;
    private double price;

    public StockData(String stockExchangeName, String timestamp, double price) {
        this.stockExchangeName = stockExchangeName;
        this.timestamp = timestamp;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getStockExchangeName() {
        return stockExchangeName;
    }
    public void setStockExchangeName(String stockExchangeName) {
        this.stockExchangeName = stockExchangeName;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
