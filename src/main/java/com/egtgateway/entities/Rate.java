package com.egtgateway.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rates")
public class Rate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(name = "base")
    private String base;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rate")
    private BigDecimal rate;

    @CreationTimestamp
    @Column(name = "[timestamp]")
    private LocalDateTime timestamp;

    public Rate(final String base, final String currency, final BigDecimal rate) {
        this(null, base, currency, rate, null);
    }
}
