// File: src/main/java/org/spring/finance_app_proyecto/DTO/StockSaleRequestDTO.java
package org.spring.finance_app_proyecto.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class StockSaleRequestDTO {

    @NotNull(message = "El ID de la acción a vender no puede ser nulo.")
    private Integer stockId; // El ID de la instancia específica de Stock que se va a vender

    @NotNull(message = "La cantidad a vender no puede ser nula.")
    @Min(value = 1, message = "La cantidad a vender debe ser al menos 1.")
    private Integer quantity;

    @NotNull(message = "El precio de venta no puede ser nulo.")
    @DecimalMin(value = "0.01", message = "El precio de venta debe ser positivo.")
    private BigDecimal sellPrice;

    @NotNull(message = "La fecha de venta no puede ser nula.")
    private LocalDate sellDate;

    // Constructor vacío (necesario para la deserialización de JSON)
    public StockSaleRequestDTO() {
    }

    // Constructor con todos los campos
    public StockSaleRequestDTO(Integer stockId, Integer quantity, BigDecimal sellPrice, LocalDate sellDate) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
    }

    // Getters y Setters
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public LocalDate getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }
}