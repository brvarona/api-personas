package com.brvarona.personas.controller;

import org.springframework.http.ResponseEntity;

import com.brvarona.personas.model.Relacion;

import io.swagger.annotations.ApiOperation;

public interface RelacionController {

	@ApiOperation(value = "Obtener la relacion que existe entre dos personas", notes = "Devuelve el vinculo existente entre dos personas")
	ResponseEntity<Relacion> getRelacion(Long persId1, Long persId2);
	
	@ApiOperation(value = "Crear una relacion padre hijo", notes = "Devuelve la relacion creada entre las dos personas")
	ResponseEntity<Relacion> asignarPadrePersona(Long persId1, Long persId2);

}
