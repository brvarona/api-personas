package com.brvarona.personas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brvarona.personas.model.Pais;
import com.brvarona.personas.model.Persona;
import com.brvarona.personas.model.TipoDocumento;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
	Optional<Persona> findByTipoDocumentoAndNroDocumentoAndPais(TipoDocumento tipoDocumento, String nroDocumento, Pais pais);
	
}