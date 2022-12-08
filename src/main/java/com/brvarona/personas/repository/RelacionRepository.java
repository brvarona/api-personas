package com.brvarona.personas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brvarona.personas.model.Relacion;

@Repository
public interface RelacionRepository extends JpaRepository<Relacion, Long> {
	
	@Query("SELECT r FROM Relacion r WHERE (r.persona1.id = ?1 AND r.persona2.id = ?2)"
			+ " OR (r.persona1.id = ?2 AND r.persona2.id = ?1) ")
	Relacion findRelacionByPersonasIds(Long persId1, Long persId2);

}