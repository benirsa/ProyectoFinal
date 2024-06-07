package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

import jakarta.servlet.http.HttpServletRequest;

public interface IUsuarioService {

	public UsuarioDto findById(String nombreUsuario);
	public UsuarioDto save(UsuarioDto usuarioRequestDto);
	public UsuarioDto update(UsuarioDto usuarioRequestDto, String username);
	public void delete(String nombreUsuario);
	public UsuarioDto findByUsuarioAndContrasena(String usuario, String contrasena);
	public ModelAndView loginUsuario(Usuario usuario, HttpServletRequest request);
	public ModelAndView registroUsuario(Usuario usuario);
	public ModelAndView logout(HttpServletRequest request);
	public List<UsuarioDto> findAll();
	public boolean existeUsuario(String username);
}
