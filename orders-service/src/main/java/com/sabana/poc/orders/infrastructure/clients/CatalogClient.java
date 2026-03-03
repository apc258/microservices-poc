package com.sabana.poc.orders.infrastructure.clients;

import com.sabana.poc.orders.infrastructure.clients.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class CatalogClient {

    private final RestClient restClient;

    public CatalogClient(@Value("${poc.catalog.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public boolean existsBySku(String sku) {
        try {
            restClient.get()
                    .uri("/products/{sku}", sku)
                    .retrieve()
                    .body(ProductResponse.class);

            return true;

        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            // Otros errores (500, timeouts, etc.) los tratamos como "no disponible"
            throw ex;
        }
    }
}