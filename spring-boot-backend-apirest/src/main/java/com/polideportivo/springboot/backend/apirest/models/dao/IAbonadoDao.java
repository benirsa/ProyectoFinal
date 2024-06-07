package com.polideportivo.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polideportivo.springboot.backend.apirest.models.entity.Abonado;

public interface IAbonadoDao extends JpaRepository<Abonado, Long>{

	// consulta jpql para obtener un abonado por su username
	@Query("SELECT a FROM Abonado a WHERE a.usuario.username = ?1")
	public Abonado findAbonadoByUsername(String username);
}
