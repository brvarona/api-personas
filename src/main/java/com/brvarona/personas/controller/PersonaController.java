package com.brvarona.personas.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.brvarona.personas.model.Persona;
import com.brvarona.personas.model.Relacion;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.payload.CrearPersonaRequest;

import io.swagger.annotations.ApiOperation;

public interface PersonaController {

	@ApiOperation(value = "Obtener todas las personas", notes = "Devuelve una lista con todas las personas")
	ResponseEntity<Page<Persona>> getAllPersonas(Pageable pageable);

	@ApiOperation(value = "Obtener una persona por id", notes = "Devuelve una persona por id")
	ResponseEntity<Persona> getPersona(Long id);

	@ApiOperation(value = "Crear una persona", notes = "Devuelve la persona creada")
	ResponseEntity<Persona> createPersona(CrearPersonaRequest personaRequest);
	
	@ApiOperation(value = "Modificar una persona", notes = "Devuelve la persona modificada")
	ResponseEntity<Persona> updatePersona(Long id, ActualizarPersonaRequest personaRequest);

	@ApiOperation(value = "Eliminar una persona", notes = "Se elimina la persona indicada")
	ResponseEntity<?> deletePersona(Long id);
	
}
