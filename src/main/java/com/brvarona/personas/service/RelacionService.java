package com.brvarona.personas.service;

import com.brvarona.personas.enums.Vinculo;
import com.brvarona.personas.model.Relacion;


public interface RelacionService {

	Relacion getRelacion(Long persId1, Long persId2);
	
	Relacion asignarRelacion(Long persId1, Long persId2, Vinculo vinculo);

}
