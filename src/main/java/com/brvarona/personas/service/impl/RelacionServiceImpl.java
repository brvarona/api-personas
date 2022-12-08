package com.brvarona.personas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brvarona.personas.enums.Vinculo;
import com.brvarona.personas.exception.ResourceNotFoundException;
import com.brvarona.personas.model.Persona;
import com.brvarona.personas.model.Relacion;
import com.brvarona.personas.repository.PersonaRepository;
import com.brvarona.personas.repository.RelacionRepository;
import com.brvarona.personas.service.RelacionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RelacionServiceImpl implements RelacionService {

	private static final String PERSONA_STR = "Persona";

	@Autowired
	private RelacionRepository relacionRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Transactional(readOnly = true)
	public Relacion getRelacion(Long persId1, Long persId2) {
		log.info("Service: getRelacion");
		
		return relacionRepository.findRelacionByPersonasIds(persId1, persId2);
	}

	public Relacion asignarRelacion(Long persId1, Long persId2, Vinculo vinculo) {
		log.info("Service: asignarRelacion");
		
		Persona persona1 = personaRepository.findById(persId1)
				.orElseThrow(() -> new ResourceNotFoundException(PERSONA_STR, "ID", persId1));

		Persona persona2 = personaRepository.findById(persId2)
				.orElseThrow(() -> new ResourceNotFoundException(PERSONA_STR, "ID", persId2));

		Relacion relacion = new Relacion();
		relacion.setPersona1(persona1);
		relacion.setPersona2(persona2);
		relacion.setVinculo(vinculo);
		return relacionRepository.save(relacion);
	}

}
