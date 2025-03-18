package com.devsu.hackerearth.backend.account.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PartialAccountDto {

	@JsonProperty("isActive")
	private Boolean isActive;
}