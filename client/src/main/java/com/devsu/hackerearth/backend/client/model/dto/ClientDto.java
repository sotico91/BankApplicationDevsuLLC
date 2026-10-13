package com.devsu.hackerearth.backend.client.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

	private Long id;
	private String dni;
	private String name;
	private String password;
	private String gender;
	private int age;
	private String address;
	private String phone;
	// Lombok names the setter setActive(); accept the "isActive" key the API documents
	@Setter(onMethod_ = @JsonAlias("isActive"))
	private boolean isActive;
}
