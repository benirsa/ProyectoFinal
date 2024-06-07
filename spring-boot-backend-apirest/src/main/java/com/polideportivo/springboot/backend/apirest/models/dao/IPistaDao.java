package com.polideportivo.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polideportivo.springboot.backend.apirest.models.entity.Pista;

public interface IPistaDao extends JpaRepository<Pista, Long> {

	// obtener pistas activas con jpql
	public List<Pista> findByEstado(String estado);
}
