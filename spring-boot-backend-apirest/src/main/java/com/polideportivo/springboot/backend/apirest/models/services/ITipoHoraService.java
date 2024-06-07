package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import com.polideportivo.springboot.backend.apirest.models.dto.tipoHora.TipoHoraDto;

public interface ITipoHoraService {

	public List<TipoHoraDto> findAll();
	
	public TipoHoraDto findById(Long id);
	
	public TipoHoraDto save(TipoHoraDto tipoHora);
	
	public TipoHoraDto update(TipoHoraDto tipoHora, Long id);
	
	public void delete(Long id);
}
