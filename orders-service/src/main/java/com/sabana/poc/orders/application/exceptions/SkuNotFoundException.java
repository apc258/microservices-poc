package com.sabana.poc.orders.application.exceptions;

public class SkuNotFoundException extends RuntimeException {

    public SkuNotFoundException(String sku) {
        super("SKU not found in catalog: " + sku);
    }
}
