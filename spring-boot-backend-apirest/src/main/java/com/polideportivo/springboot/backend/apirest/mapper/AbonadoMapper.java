package com.polideportivo.springboot.backend.apirest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.abonado.AbonadoDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Abonado;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Mapper(componentModel = "spring")
public interface AbonadoMapper {

	Abonado dtoToEntity(AbonadoDto dto);
	AbonadoDto entityToDto(Abonado abonado);
	List<AbonadoDto> entityListToDtoList(List<Abonado> abonado);
	UsuarioDto usuarioEntityToDto(Usuario usuario);
}
