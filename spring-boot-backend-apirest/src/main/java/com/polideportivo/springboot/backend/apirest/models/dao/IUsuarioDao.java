package com.polideportivo.springboot.backend.apirest.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, String>{

	// consulta jpql para obtener usuario por usuario y contraseña
	@Query("SELECT u FROM Usuario u WHERE u.username = ?1 and u.password = ?2")
	public Usuario findUsuarioByUsuarioAndContrasena(String usuario, String contrasena);
	
	// consulta jpql para obtener un usuario por nombre de usuario
	@Query("SELECT u FROM Usuario u WHERE u.username = ?1")
	public Optional<Usuario> findUsuarioByUsuario(String usuario);
}
