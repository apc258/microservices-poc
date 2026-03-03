package com.sabana.poc.inventory.infraestructure.messaging;

import com.sabana.poc.inventory.infraestructure.persistence.StockEntity;
import com.sabana.poc.inventory.infraestructure.persistence.StockRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderCreatedListener {

    private final StockRepository repository;

    public OrderCreatedListener(StockRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${poc.kafka.topic.order-created}", groupId = "inventory-service")
    @Transactional
    public void handle(OrderCreatedEvent event) {

        StockEntity stock = repository.findById(event.sku())
                .orElse(new StockEntity(event.sku(), 100)); // stock inicial demo

        stock.decrease(event.quantity());

        repository.save(stock);

        System.out.println("Stock updated for SKU: " + event.sku());
    }
}