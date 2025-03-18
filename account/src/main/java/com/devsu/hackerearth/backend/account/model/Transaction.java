package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
