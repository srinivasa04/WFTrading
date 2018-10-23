package com.worldfirst.fx.trading.util;

/**
 * TradeOrder type enum constants.
 */
public enum OrderTypeEnum {

    BID("BID"),ASK("ASK");

    private String value;

    OrderTypeEnum(String type) {
        this.value = type;
    }

    public String value() {
        return value;
    }
}
