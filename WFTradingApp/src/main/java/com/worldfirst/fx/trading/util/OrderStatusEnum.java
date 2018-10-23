package com.worldfirst.fx.trading.util;

/**
 * Holding different status of Order like Pending, Cancel, Executed.
 */
public enum OrderStatusEnum {

    PENDING("PENDING"),EXECUTED("EXECUTED"),CANCELLED("CANCELLED");

    private String value;

    OrderStatusEnum(String status){
        this.value=status;
    }

    public String value() {
        return value;
    }
}
