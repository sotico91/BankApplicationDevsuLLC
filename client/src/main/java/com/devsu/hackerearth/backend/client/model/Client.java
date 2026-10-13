package com.devsu.hackerearth.backend.client.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@DiscriminatorValue("CLIENT")
public class Client {

	@Id
	private Long id;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id")
	private Person person;

	private String password;
	private boolean isActive;
}
