package com.brvarona.personas.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	private String apellido;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id", referencedColumnName="id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private TipoDocumento tipoDocumento;
	
	@NotBlank
	private String nroDocumento;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pais_id", referencedColumnName="id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Pais pais;
	
	@NotNull
    @Min(value = 18)
	private Integer edad;
	
	@NotBlank
	@Email
	private String email;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "persona1", cascade = CascadeType.REMOVE)
    private List<Relacion> relaciones;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "persona2", cascade = CascadeType.REMOVE)
    private List<Relacion> relaciones2;
	
}
