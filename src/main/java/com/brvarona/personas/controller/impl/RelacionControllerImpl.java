package com.brvarona.personas.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brvarona.personas.advice.TrackExecutionTime;
import com.brvarona.personas.controller.RelacionController;
import com.brvarona.personas.enums.Vinculo;
import com.brvarona.personas.model.Relacion;
import com.brvarona.personas.service.RelacionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RelacionControllerImpl implements RelacionController {

	@Autowired
	RelacionService relacionService;
	
	@GetMapping("/relaciones/{persId1}/{persId2}")
	@TrackExecutionTime
	public ResponseEntity<Relacion> getRelacion(@PathVariable(value = "persId1") Long persId1, 
												@PathVariable(value = "persId2") Long persId2) {
		log.info("Obteniendo relaciones entre las personas {} y {}", persId1, persId2);

		return ResponseEntity.ok(relacionService.getRelacion(persId1, persId2));
	}


	@PostMapping("/personas/{persId1}/padre/{persId2}")
	@TrackExecutionTime
	public ResponseEntity<Relacion> asignarPadrePersona(@PathVariable(value = "persId1") Long persId1, 
														@PathVariable(value = "persId2") Long persId2) {
		log.info("Asignando relacion padre-hijo entre las personas {} y {}", persId1, persId2);

		return ResponseEntity.ok(relacionService.asignarRelacion(persId1, persId2, Vinculo.PADRE));
	}
	
}
