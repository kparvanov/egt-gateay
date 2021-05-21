package com.egtgateway.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @Column(name = "request_id")
    private String requestId;

    @Column(name = "[timestamp]")
    private LocalDateTime timestamp;

    @Column(name = "client")
    private String client;

    @Column(name = "currency")
    private String currency;

    @Column(name = "period")
    private Integer period;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "endpoint")
    private String endpoint;
}
