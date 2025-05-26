package org.spring.finance_app_proyecto.DTO;

import java.time.LocalDate;

public class StockFilterDTO {
    private String ticker;
    private LocalDate purchaseDate;

    public StockFilterDTO() {}

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}