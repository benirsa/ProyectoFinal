package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import com.polideportivo.springboot.backend.apirest.models.dto.pista.PistaDto;

public interface IPistaService {

	public List<PistaDto> findAll();
	
	public PistaDto findById(Long id);
	
	public PistaDto save(PistaDto pista);
	
	public PistaDto update (PistaDto pista, Long id);
	
	public void delete(Long id);
	
	public List<PistaDto> findByEstado(String estado);
}
