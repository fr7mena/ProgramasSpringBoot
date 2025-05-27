// File: src/main/java/org/spring/finance_app_proyecto/DTO/StockSaleResponseDTO.java
package org.spring.finance_app_proyecto.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StockSaleResponseDTO {
    private Integer id; // ID de la venta recién creada
    private String ticker; // Ticker de la acción vendida
    private Integer soldQuantity; // Cantidad de acciones vendidas
    private BigDecimal sellPrice; // Precio al que se vendieron
    private LocalDate sellDate; // Fecha en que se realizó la venta
    private BigDecimal totalGainLoss; // Ganancia o pérdida total de la venta
    private String message; // Mensaje de confirmación o detalle de la operación

    // Constructor vacío
    public StockSaleResponseDTO() {
    }

    // Constructor con todos los campos
    public StockSaleResponseDTO(Integer id, String ticker, Integer soldQuantity, BigDecimal sellPrice, LocalDate sellDate, BigDecimal totalGainLoss, String message) {
        this.id = id;
        this.ticker = ticker;
        this.soldQuantity = soldQuantity;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
        this.totalGainLoss = totalGainLoss;
        this.message = message;
    }

    // Getters y Setters
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

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
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

    public BigDecimal getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(BigDecimal totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}