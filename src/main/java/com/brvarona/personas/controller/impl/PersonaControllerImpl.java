package com.brvarona.personas.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brvarona.personas.advice.TrackExecutionTime;
import com.brvarona.personas.controller.PersonaController;
import com.brvarona.personas.model.Persona;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.payload.CrearPersonaRequest;
import com.brvarona.personas.service.PersonaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/personas")
public class PersonaControllerImpl implements PersonaController {

	@Autowired
	PersonaService personaService;
	
	
	@GetMapping
	public ResponseEntity<Page<Persona>> getAllPersonas(Pageable pageable) {
		log.info("Obteniendo todas las personas");
		
		return ResponseEntity.ok(personaService.getAllPersonas(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Persona> getPersona(@PathVariable(value = "id") Long id) {
		log.info("Obteniendo persona por id: {}", id);

		return ResponseEntity.ok(personaService.getPersona(id));
	}
	
	@PostMapping
	@TrackExecutionTime
	public ResponseEntity<Persona> createPersona(@Valid @RequestBody CrearPersonaRequest personaRequest) {
		log.info("Creando una nueva persona con tipoDocId: {}, nroDoc: {} y paisId: {}", 
				personaRequest.getTipoDocumentoId(), personaRequest.getNroDocumento(), personaRequest.getPaisId());

		return ResponseEntity.ok(personaService.createPersona(personaRequest));
	}

	@PutMapping("/{id}")
	@TrackExecutionTime
	public ResponseEntity<Persona> updatePersona(@PathVariable(value = "id") Long id,
												 @Valid @RequestBody ActualizarPersonaRequest personaRequest) {
		log.info("Modificando la persona con id: {}", id);

		return ResponseEntity.ok(personaService.updatePersona(id, personaRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePersona(@PathVariable(value = "id") Long id) {
		log.info("Eliminando la persona con id: {}", id);
		personaService.deletePersona(id);

		return ResponseEntity.noContent().build();
	}
}
