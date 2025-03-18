package com.devsu.hackerearth.backend.client.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

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
