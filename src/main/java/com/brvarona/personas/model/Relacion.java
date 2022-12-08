package com.brvarona.personas.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.brvarona.personas.enums.Vinculo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "relaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pers_id_1")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Persona persona1;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pers_id_2")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Persona persona2;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Vinculo vinculo;
	
}
