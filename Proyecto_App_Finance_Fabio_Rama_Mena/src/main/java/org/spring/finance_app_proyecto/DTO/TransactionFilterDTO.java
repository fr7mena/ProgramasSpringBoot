// File: src/main/java/org/spring/finance_app_proyecto/DTO/TransactionFilterDTO.java

package org.spring.finance_app_proyecto.DTO;

import java.time.LocalDate;

public class TransactionFilterDTO {
    private String type;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;

    public TransactionFilterDTO() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
