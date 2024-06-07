package com.polideportivo.springboot.backend.apirest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.polideportivo.springboot.backend.apirest.models.dto.tipoHora.TipoHoraDto;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoHora;

@Mapper(componentModel = "spring")
public interface TipoHoraMapper {

	TipoHora dtoToEntity(TipoHoraDto dto);
	TipoHoraDto entityToDto(TipoHora tipoHora);
	List<TipoHoraDto> entityListToDtoList(List<TipoHora> tipoHora);
}
