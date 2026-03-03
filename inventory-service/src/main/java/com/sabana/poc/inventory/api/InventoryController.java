package com.sabana.poc.inventory.api;

import com.sabana.poc.inventory.infraestructure.persistence.StockEntity;
import com.sabana.poc.inventory.infraestructure.persistence.StockRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final StockRepository repository;

    public InventoryController(StockRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{sku}")
    public ResponseEntity<StockEntity> get(@PathVariable String sku) {
        return repository.findById(sku)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}