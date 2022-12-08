package com.brvarona.personas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.brvarona.personas.model.Persona;
import com.brvarona.personas.payload.ActualizarPersonaRequest;
import com.brvarona.personas.payload.CrearPersonaRequest;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonaMapper {
	
    @Mapping(target="pais", source = "paisId", ignore = true)
    @Mapping(target="tipoDocumento", source = "tipoDocumentoId", ignore = true)
	Persona fromCreateRequest(CrearPersonaRequest request);
	
	Persona fromUpdateRequest(@MappingTarget Persona element, ActualizarPersonaRequest request);

}