package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.models.dto.abonado.AbonadoDto;

public interface IAbonadoService {

	public List<AbonadoDto> findAll();
	
	public AbonadoDto findById(Long id);
	
	public ModelAndView save(AbonadoDto abonado);
	
	public AbonadoDto update(AbonadoDto abonado, Long id);
	
	public void delete(Long id);
}
