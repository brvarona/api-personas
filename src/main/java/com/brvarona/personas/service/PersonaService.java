package com.brvarona.personas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.brvarona.personas.model.Persona;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.payload.CrearPersonaRequest;


public interface PersonaService {

	Page<Persona> getAllPersonas(Pageable pageable);

	Persona getPersona(Long id);

	Persona createPersona(CrearPersonaRequest persona);

	Persona updatePersona(Long id, ActualizarPersonaRequest persona);

	void deletePersona(Long id);

}
