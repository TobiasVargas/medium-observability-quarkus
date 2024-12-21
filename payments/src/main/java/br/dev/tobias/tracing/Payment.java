package br.dev.tobias.tracing;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "payments", name = "payments")
public class Payment extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String buyerDocument;
    private BigDecimal amount;

    // Hibernate constructor
    public Payment() {
    }

    public Payment(String buyerDocument, BigDecimal amount) {
        this.buyerDocument = buyerDocument;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getBuyerDocument() {
        return buyerDocument;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
