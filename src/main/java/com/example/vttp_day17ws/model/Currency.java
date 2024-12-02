package com.example.vttp_day17ws.model;

public class Currency {
    private String currencyCode;
    private Double rate;
    public Currency() {
    }
    public Currency(String currencyCode, Double rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public Double getRate() {
        return rate;
    }
    public void setRate(Double rate) {
        this.rate = rate;
    }
    
}
