package com.brvarona.personas.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActualizarPersonaRequest {
	
	private String nombre;
	
	private String apellido;
	
    @Min(value = 18, message = "La edad de la persona no debe ser menor de 18")
	private Integer edad;
	
	@Email
	private String email;

}
