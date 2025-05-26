// File: src/main/java/org/spring/finance_app_proyecto/DTO/StockCreateDTO.java
package org.spring.finance_app_proyecto.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StockCreateDTO {
    private Integer id; // Añadido el ID para poder devolverlo en las consultas de visualización
    @Size(max = 10)
    @NotNull
    private String ticker;

    @NotNull
    private BigDecimal purchasePrice;

    @NotNull
    private Integer quantity;

    @NotNull
    private LocalDate purchaseDate;

    // Getter y Setter para 'id'
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}