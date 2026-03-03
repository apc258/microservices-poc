package com.sabana.poc.orders.api.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderResponse(UUID id, String sku, int quantity, String status, Instant createdAt) {}
