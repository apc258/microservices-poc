package com.sabana.poc.inventory.infraestructure.messaging;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String sku,
        int quantity,
        Instant createdAt
) {}

