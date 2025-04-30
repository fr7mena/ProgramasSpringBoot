package org.spring.finance_app_proyecto.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private org.spring.finance_app_proyecto.Model.User user;

    @Size(max = 10)
    @NotNull
    @Column(name = "ticker", nullable = false, length = 10)
    private String ticker;

    @NotNull
    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice; //Recuerda que al estar el nombre diferente podria dar problemas pero el Chat comenta que el mappeo es explicito y no deberia dar problemas

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

//    @ColumnDefault("curdate()")
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now(); //PUSE AQUI LA INICIALIZACION A LOCALDATE.NOW() PORQUE ME LO PIDIO EL CHAT;

    @OneToMany(mappedBy = "stock")
    private Set<StockSale> stockSales = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public org.spring.finance_app_proyecto.Model.User getUser() {
        return user;
    }

    public void setUser(org.spring.finance_app_proyecto.Model.User user) {
        this.user = user;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<StockSale> getStockSales() {
        return stockSales;
    }

    public void setStockSales(Set<StockSale> stockSales) {
        this.stockSales = stockSales;
    }

}