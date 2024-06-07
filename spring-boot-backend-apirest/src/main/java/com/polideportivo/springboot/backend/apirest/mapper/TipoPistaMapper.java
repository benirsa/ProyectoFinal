package com.polideportivo.springboot.backend.apirest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.tipoPista.TipoPistaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoPista;

@Mapper(componentModel = "spring")
public interface TipoPistaMapper {

	TipoPista dtoToEntity(TipoPistaDto dto);
	TipoPistaDto entityToDto(TipoPista tipoPista);
	List<TipoPistaDto> entityListToDtoList(List<TipoPista> tipoPista);
}
