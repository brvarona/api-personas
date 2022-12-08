package com.brvarona.personas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brvarona.personas.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
	
}