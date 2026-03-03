package com.sabana.poc.orders.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Order(UUID id, String customerId, BigDecimal amount, String status, Instant createdAt) {
}
