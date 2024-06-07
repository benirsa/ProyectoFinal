package com.polideportivo.springboot.backend.apirest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.reserva.ReservaDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Reserva;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

	Reserva dtoToEntity(ReservaDto dto);
	ReservaDto entityToDto(Reserva reserva);
	List<ReservaDto> entityListToResponseDtoList(List<Reserva> reserva);
	UsuarioDto usuarioEntityToDto(Usuario usuario);
}
