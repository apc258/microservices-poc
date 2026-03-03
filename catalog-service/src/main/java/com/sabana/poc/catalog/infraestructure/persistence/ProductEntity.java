package com.sabana.poc.catalog.infraestructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price; // para PoC, entero (pesos) o centavos

    protected ProductEntity() { }

    public ProductEntity(String sku, String name, int price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public int getPrice() { return price; }
}