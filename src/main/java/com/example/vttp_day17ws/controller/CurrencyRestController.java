package com.example.vttp_day17ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vttp_day17ws.model.Currency;
import com.example.vttp_day17ws.service.CurrencyService;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyRestController {
    @Autowired CurrencyService currencyService;

    @GetMapping("")
    public ResponseEntity<List<Currency>> getCurrencies(){
        List<Currency> currencies = new ArrayList<>();
        currencies = currencyService.getCurrencies();
        return ResponseEntity.ok().body(currencies);
    }
    
}
