package com.sabana.poc.catalog.api;

import com.sabana.poc.catalog.api.dto.CreateProductRequest;
import com.sabana.poc.catalog.api.dto.ProductResponse;
import com.sabana.poc.catalog.infraestructure.persistence.ProductEntity;
import com.sabana.poc.catalog.infraestructure.persistence.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class CatalogController {

    private final ProductRepository repository;

    public CatalogController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest req) {
        if (req == null || req.sku() == null || req.sku().isBlank() || req.name() == null || req.name().isBlank() || req.price() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        ProductEntity entity = new ProductEntity(req.sku().trim(), req.name().trim(), req.price());
        repository.save(entity);

        ProductResponse resp = new ProductResponse(entity.getSku(), entity.getName(), entity.getPrice());
        return ResponseEntity.created(URI.create("/products/" + entity.getSku())).body(resp);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductResponse> get(@PathVariable String sku) {
        return repository.findById(sku)
                .map(p -> ResponseEntity.ok(new ProductResponse(p.getSku(), p.getName(), p.getPrice())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
