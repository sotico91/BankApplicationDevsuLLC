package com.devsu.hackerearth.backend.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Account extends Base {

    @Column(unique = true, nullable = false)
    private String number;
    private String type;
    private double initialAmount;
    private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;
}
