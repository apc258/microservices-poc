package com.sabana.poc.orders.api.dto;


public record CreateOrderRequest(String sku, int quantity) {
}
