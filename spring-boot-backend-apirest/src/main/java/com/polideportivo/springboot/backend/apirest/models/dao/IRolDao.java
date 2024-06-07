package com.polideportivo.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polideportivo.springboot.backend.apirest.models.entity.Rol;

public interface IRolDao extends JpaRepository<Rol, Long>{

}
