package com.brvarona.personas.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CrearPersonaRequest {
	
	@NotBlank(message="Campo nombre es requerido")
	private String nombre;
	
	@NotBlank(message="Campo apellido es requerido")
	private String apellido;

	@NotNull(message="Campo tipoDocumentoId es requerido")
	private Long tipoDocumentoId;
	
	@NotBlank(message="Campo nroDocumento es requerido")
	private String nroDocumento;

	@NotNull(message="Campo paisId es requerido")
	private Long paisId;
	
	@NotNull(message="Campo edad es requerido")
    @Min(value = 18, message = "La edad de la persona no debe ser menor de 18")
	private Integer edad;
	
	@NotBlank(message="Campo email es requerido")
	@Email
	private String email;

}
