package org.spring.finance_app_proyecto.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private org.spring.finance_app_proyecto.Model.User user;

    @NotNull
//    @Lob Me ha dicho el Chat que lo quite debido a que puede dar problemas porque Hibernate lo reconoce como un texto grande y realmente en corto, ademas esto puede romper consultas y paginación en algunas bases de datos
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 100)
    @Column(name = "category", length = 100)
    private String category;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

//    @ColumnDefault("curdate()")
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now(); //PUSE AQUI LA INICIALIZACION A LOCALDATE.NOW() PORQUE ME LO PIDIO EL CHAT= LocalDate.now(); //PUSE AQUI LA INICIALIZACION A LOCALDATE.NOW() PORQUE ME LO PIDIO EL CHAT;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

}