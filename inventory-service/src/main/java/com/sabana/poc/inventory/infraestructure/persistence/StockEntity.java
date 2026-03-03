package com.sabana.poc.inventory.infraestructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private int quantity;

    protected StockEntity() {
    }

    public StockEntity(String sku, int quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decrease(int amount) {
        this.quantity -= amount;
    }

    public void increase(int amount) {
        this.quantity += amount;
    }
}
