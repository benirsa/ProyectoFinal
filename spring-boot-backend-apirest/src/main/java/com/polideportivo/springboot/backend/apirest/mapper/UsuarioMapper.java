package com.polideportivo.springboot.backend.apirest.mapper;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	UsuarioDto entityToDto(Usuario usuario);
	Usuario dtoToEntity(UsuarioDto usuarioResponseDto);
}
