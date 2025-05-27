package org.spring.finance_app_proyecto.DTO;

import java.math.BigDecimal;
import java.util.List;

public class UserSalesSummaryDTO {
    private List<StockSaleResponseDTO> sales; // Lista de ventas individuales
    private BigDecimal totalGainLoss;        // Ganancia o pérdida total acumulada
    private BigDecimal percentageGainLoss;   // Porcentaje total de ganancia o pérdida

    // Constructor vacío (necesario para la deserialización de JSON)
    public UserSalesSummaryDTO() {
    }

    // Constructor con todos los campos
    public UserSalesSummaryDTO(List<StockSaleResponseDTO> sales, BigDecimal totalGainLoss, BigDecimal percentageGainLoss) {
        this.sales = sales;
        this.totalGainLoss = totalGainLoss;
        this.percentageGainLoss = percentageGainLoss;
    }

    // Getters y Setters
    public List<StockSaleResponseDTO> getSales() {
        return sales;
    }

    public void setSales(List<StockSaleResponseDTO> sales) {
        this.sales = sales;
    }

    public BigDecimal getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(BigDecimal totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
    }

    public BigDecimal getPercentageGainLoss() {
        return percentageGainLoss;
    }

    public void setPercentageGainLoss(BigDecimal percentageGainLoss) {
        this.percentageGainLoss = percentageGainLoss;
    }
}