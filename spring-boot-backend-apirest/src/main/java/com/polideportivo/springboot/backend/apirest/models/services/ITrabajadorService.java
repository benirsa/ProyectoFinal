package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import com.polideportivo.springboot.backend.apirest.models.dto.trabajador.TrabajadorDto;

public interface ITrabajadorService {

	public List<TrabajadorDto> findAll();
	
	public TrabajadorDto findById(Long id);
	
	public TrabajadorDto save(TrabajadorDto usuario);
	
	public TrabajadorDto update(TrabajadorDto usuario, Long id);
	
	public void delete(Long id);
}
