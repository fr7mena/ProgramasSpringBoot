package org.spring.finance_app_proyecto.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stock_sales")
public class StockSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "stock_id", nullable = false)
    private org.spring.finance_app_proyecto.Model.Stock stock;

    @NotNull
    @Column(name = "sell_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "sell_date", nullable = false)
    private LocalDate sellDate;

//    @ColumnDefault("curdate()")
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now(); //PUSE AQUI LA INICIALIZACION A LOCALDATE.NOW() PORQUE ME LO PIDIO EL CHAT;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public org.spring.finance_app_proyecto.Model.Stock getStock() {
        return stock;
    }

    public void setStock(org.spring.finance_app_proyecto.Model.Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

}