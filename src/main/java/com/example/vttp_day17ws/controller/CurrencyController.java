package com.example.vttp_day17ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.vttp_day17ws.model.Currency;
import com.example.vttp_day17ws.service.CurrencyService;

@Controller
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @GetMapping("/")
    public String landingPage(Model model) {
        // get currencies
        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute("currencies", currencies);
        return "index";
    }

    @PostMapping("/convert")
    public String convertCurrency(@RequestParam String fromCurrency,
            @RequestParam String toCurrency, @RequestParam Double amount, Model model) {
                try {
                    double convertedAmount = currencyService.convertCurrency(fromCurrency, toCurrency, amount);
                    model.addAttribute("fromAmount", String.format("%.2f", amount));
                    model.addAttribute("fromCurrency", fromCurrency);
                    model.addAttribute("toAmount", String.format("%.2f", convertedAmount));
                    model.addAttribute("toCurrency", toCurrency);
            
                    return "result";
                } catch (Exception e){
                    model.addAttribute("error", "Error during conversion " + e.getMessage());
                    return "index";
                }
        
    }

}
