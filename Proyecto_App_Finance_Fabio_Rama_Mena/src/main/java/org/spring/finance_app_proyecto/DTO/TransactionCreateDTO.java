package org.spring.finance_app_proyecto.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionCreateDTO {
    private Integer id; // Añade el ID
    private String type;
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDate transactionDate;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
}