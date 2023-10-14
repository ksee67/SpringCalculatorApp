package com.example.demo.controller;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/calculator")
    public String calculatorPage() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam String expression, Model model) {
        double result = evaluateExpression(expression);
        model.addAttribute("expression", expression);
        model.addAttribute("result", result);
        return "result";
    }

    private double evaluateExpression(String expression) {
        try {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(expression);
            return exp.getValue(Double.class);
        } catch (Exception e) {
            //  ошибка
            return Double.NaN;
        }
    }
    @GetMapping("/currencyconverter")
    public String currencyConverterPage() {
        return "currencyconverter";
    }
    @PostMapping("/convert")
    public String convertCurrency(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam double amount, Model model) {
        double conversionRate = getConversionRate(fromCurrency, toCurrency); //  курс обмена
        double convertedAmount = amount * conversionRate;

        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("amount", amount);
        model.addAttribute("convertedAmount", convertedAmount);

        return "currencyconverterresult";
    }

    private double getConversionRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
            return 0.88;
        } else if (fromCurrency.equals("USD") && toCurrency.equals("TRY")) {
            return 9.55;
        }
        return 1.0; // еысли одинаковые валюты
    }

}
