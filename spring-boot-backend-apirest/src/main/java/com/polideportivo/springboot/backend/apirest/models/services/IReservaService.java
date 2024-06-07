package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.List;

import com.polideportivo.springboot.backend.apirest.models.dto.reserva.ReservaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Reserva;

public interface IReservaService {

	public List<ReservaDto> findAll();
	
	public List<ReservaDto> findAllByAbonadoId(Long id);
	
	public ReservaDto findById(Long id);
	
	public ReservaDto save(ReservaDto reserva);
	
	public ReservaDto update(ReservaDto reserva, Long id);
	
	public void delete(Long id);
	
	public Double calcularPrecioReserva(Reserva reserva);
}
