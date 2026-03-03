package com.sabana.poc.orders.application;

import com.sabana.poc.orders.api.dto.CreateOrderRequest;
import com.sabana.poc.orders.api.dto.OrderResponse;
import com.sabana.poc.orders.application.exceptions.OrderNotFoundException;
import com.sabana.poc.orders.application.exceptions.SkuNotFoundException;
import com.sabana.poc.orders.infrastructure.messaging.OrderCreatedEvent;
import com.sabana.poc.orders.infrastructure.messaging.OrderEventPublisher;
import com.sabana.poc.orders.infrastructure.persistence.OrderEntity;
import com.sabana.poc.orders.infrastructure.persistence.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sabana.poc.orders.infrastructure.clients.CatalogClient;

import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderEventPublisher publisher;
    private final CatalogClient catalogClient;

    public OrderService(OrderRepository repository, OrderEventPublisher publisher, CatalogClient catalogClient) {
        this.repository = repository;
        this.publisher = publisher;
        this.catalogClient = catalogClient;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest req) {
        if (req == null || req.sku() == null || req.sku().isBlank() || req.quantity() <= 0) {
            throw new IllegalArgumentException("Invalid request: sku and quantity are required.");
        }

        String sku = req.sku().trim();

        if (!catalogClient.existsBySku(sku)) {
            throw new SkuNotFoundException(sku);
        }

        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        OrderEntity entity = new OrderEntity(id, req.sku().trim(), req.quantity(), "CREATED", now);
        repository.save(entity);

        publisher.publishOrderCreated(new OrderCreatedEvent(id, entity.getSku(), entity.getQuantity(), now));

        return new OrderResponse(entity.getId(), entity.getSku(), entity.getQuantity(), entity.getStatus(), entity.getCreatedAt());
    }

    public OrderResponse get(UUID id) {
        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return new OrderResponse(entity.getId(), entity.getSku(), entity.getQuantity(), entity.getStatus(), entity.getCreatedAt());
    }
}
