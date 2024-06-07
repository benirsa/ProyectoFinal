package com.polideportivo.springboot.backend.apirest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.pista.PistaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Pista;

@Mapper(componentModel = "spring")
public interface PistaMapper {

	Pista dtoToEntity(PistaDto dto);
	PistaDto entityToDto(Pista pista);
	List<PistaDto> entityListToDtoList(List<Pista> pista);
}
