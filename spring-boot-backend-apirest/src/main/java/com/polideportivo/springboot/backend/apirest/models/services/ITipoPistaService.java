package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import com.polideportivo.springboot.backend.apirest.models.dto.tipoPista.TipoPistaDto;

public interface ITipoPistaService {

	public List<TipoPistaDto> findAll();
	
	public TipoPistaDto findById(Long id);
	
	public TipoPistaDto save(TipoPistaDto tipoPista);
	
	public TipoPistaDto update(TipoPistaDto tipoPista, Long id);
	
	public void delete(Long id);
}
