package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionDto {

	private Long id;
	private Date date;
	private String type;
	private double amount;
	private double balance;
	private Long accountId;
}
