package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Transaction extends Base {

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String type;
	private double amount;
	private double balance;

	@Column(name = "account_id", nullable = false)
	private Long accountId;
}
