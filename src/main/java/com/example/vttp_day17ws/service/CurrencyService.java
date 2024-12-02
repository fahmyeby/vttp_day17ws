package com.example.vttp_day17ws.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.vttp_day17ws.model.Currency;
import com.example.vttp_day17ws.repo.RedisRepo;
import com.example.vttp_day17ws.util.Util;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class CurrencyService {
    @Autowired
    RedisRepo currencyRepo;

    RestTemplate rt = new RestTemplate();
    // method to fetch API data, convert from JSON to object
    public List<Currency> getCurrencies() {
        try { // for error handling

            // build url with params
            String url = UriComponentsBuilder
            .fromUriString(Util.apiURL)
            .queryParam("access_key", Util.apiKEY)
            .queryParam("format", 1)
            .toUriString();


            String currencyData = rt.getForObject(url, String.class);
            if (currencyData == null) {
                throw new RuntimeException("No data from API");
            }
            JsonReader jReader = Json.createReader(new StringReader(currencyData));
            JsonObject jObject = jReader.readObject();

            JsonObject jDataObject = jObject.getJsonObject("rates");

            List<Currency> currencies = new ArrayList<>();

            Set<Entry<String, JsonValue>> entries = jDataObject.entrySet();

            for (Entry<String, JsonValue> entry : entries) {
                Currency c = new Currency();
                c.setCurrencyCode(entry.getKey());
                c.setRate(((JsonNumber) entry.getValue()).doubleValue());
                currencies.add(c);
            }
            return currencies;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching data: " + e.getMessage());
        }
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount){
        List<Currency> currencies = getCurrencies();

        // find rates for both currencies
        double fromRate = currencies.stream()
        .filter(c -> c.getCurrencyCode().equals(fromCurrency))
        .findFirst().map(Currency::getRate)
        .orElseThrow(() -> new RuntimeException("Currency not found: " + fromCurrency));

        double toRate = currencies.stream()
        .filter(c -> c.getCurrencyCode().equals(fromCurrency))
        .findFirst().map(Currency::getRate)
        .orElseThrow(() -> new RuntimeException("Currency not found: " + fromCurrency));

        //conversion
        Double convertedAmount = amount * (toRate/fromRate);
        return convertedAmount;
    }
}
