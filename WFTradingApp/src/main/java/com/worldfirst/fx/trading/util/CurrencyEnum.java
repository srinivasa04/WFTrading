package com.worldfirst.fx.trading.util;

/**
 * CurrencyEnum type enum for different currencies.
 */
public enum CurrencyEnum {

    GBP_USD("GBP/USD"),USD_GBP("USD/GBP"),EUR_USD("EUR/USD");

    private String value;

    CurrencyEnum(String currency) {
        this.value = currency;
    }

    public String value() {
        return value;
    }
}
