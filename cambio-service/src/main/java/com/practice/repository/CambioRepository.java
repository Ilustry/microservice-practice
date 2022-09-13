package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{

	Cambio findByFromAndTo(String from, String to);
	
}
