package com.brvarona.personas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brvarona.personas.exception.EntityAlreadyExistsException;
import com.brvarona.personas.exception.ResourceNotFoundException;
import com.brvarona.personas.mapper.PersonaMapper;
import com.brvarona.personas.model.Persona;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.payload.CrearPersonaRequest;
import com.brvarona.personas.repository.PaisRepository;
import com.brvarona.personas.repository.PersonaRepository;
import com.brvarona.personas.repository.TipoDocumentoRepository;
import com.brvarona.personas.service.PersonaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonaServiceImpl implements PersonaService {

	private static final String PERSONA_STR = "Persona";

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	@Autowired
	private PersonaMapper mapper;

	@Transactional(readOnly = true)
	@Cacheable("personas")
	public Page<Persona> getAllPersonas(Pageable pageable) {
		log.info("Service: getAllPersonas");
		return personaRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Cacheable("persona")
	public Persona getPersona(Long id) {
		log.info("Service: getPersona, id: {}", id);
		return personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PERSONA_STR, "ID", id));
	}

	@Caching(evict = { @CacheEvict(value = "personas", allEntries = true),
			@CacheEvict(value = "persona", allEntries = true) })
	public Persona createPersona(CrearPersonaRequest personaRequest) {
		log.info("Service: createPersona");
		
		var pais = paisRepository.findById(personaRequest.getPaisId())
				.orElseThrow(() -> new ResourceNotFoundException("Pais", "ID", personaRequest.getPaisId()));
		
		var tipoDocumento = tipoDocumentoRepository.findById(personaRequest.getTipoDocumentoId())
				.orElseThrow(() -> new ResourceNotFoundException("Tipo documento", "ID", personaRequest.getTipoDocumentoId()));
		
		personaRepository.findByTipoDocumentoAndNroDocumentoAndPais(tipoDocumento, personaRequest.getNroDocumento(), pais)	
						 .ifPresent(data -> { 
							 	throw new EntityAlreadyExistsException("La persona que intenta crear ya existe"); 
					     });
		
		var persona = mapper.fromCreateRequest(personaRequest);
		persona.setPais(pais);
		persona.setTipoDocumento(tipoDocumento);

		return personaRepository.save(persona);
	}

	@Caching(evict = { @CacheEvict(value = "personas", allEntries = true),
			@CacheEvict(value = "persona", allEntries = true) })
	public Persona updatePersona(Long id, ActualizarPersonaRequest personaRequest) {
		log.info("Service: updatePersona, id: {}", id);
		var persona = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(PERSONA_STR, "ID", id));

		return personaRepository.save(mapper.fromUpdateRequest(persona, personaRequest));
	}

	@Caching(evict = { @CacheEvict(value = "personas", allEntries = true),
			@CacheEvict(value = "persona", allEntries = true) })
	public void deletePersona(Long id) {
		log.info("Service: deletePersona, id: {}", id);
		var superhero = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(PERSONA_STR, "ID", id));

		personaRepository.delete(superhero);
	}

}
