package com.sabana.poc.inventory.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockEntity, String> {
}
