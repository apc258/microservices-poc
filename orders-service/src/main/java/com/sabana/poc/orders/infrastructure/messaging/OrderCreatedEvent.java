package com.sabana.poc.orders.infrastructure.messaging;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, String sku, int quantity, Instant createdAt) {}
