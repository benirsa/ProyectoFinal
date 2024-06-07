package com.polideportivo.springboot.backend.apirest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.trabajador.TrabajadorDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Trabajador;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Mapper(componentModel = "spring")
public interface TrabajadorMapper {

	Trabajador dtoToEntity(TrabajadorDto dto);
	Usuario usuarioDtoToUsuarioEntity(UsuarioDto usuarioDto);
	UsuarioDto usuarioEntityToUsuarioDto(Usuario usuario);
	TrabajadorDto entityToDto(Trabajador tranajador);
	List<TrabajadorDto> entityListToDtoList(List<Trabajador> usuario);
	List<Trabajador> dtoListToEntityList(List<TrabajadorDto> usuario);
}
