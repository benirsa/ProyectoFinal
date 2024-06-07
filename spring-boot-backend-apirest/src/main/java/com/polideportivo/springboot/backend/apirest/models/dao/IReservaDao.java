package com.polideportivo.springboot.backend.apirest.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polideportivo.springboot.backend.apirest.models.entity.Reserva;

public interface IReservaDao extends JpaRepository<Reserva, Long> {

	// consulta jpql para obtener el número de reservas de un abonado por su dni
	@Query("SELECT COUNT(r) FROM Reserva r WHERE r.abonado.dni = ?1")
	public long findCountByAbonadoDni(String dni);
	
	// consulta jpql para obtener las pistas reservadas por fecha y el tipo de la pista
	@Query("SELECT r FROM Reserva r WHERE r.fechaReserva = ?1 and r.pista.id = ?2")
	public List<Reserva> findReservaByFechaReservaAndPistaId(Date fechaReserva, Long idPista);
	
	// consulta jpql para obtener las pistas reservadas por un abonado
	@Query("SELECT r FROM Reserva r WHERE r.abonado.id = ?1")
	public List<Reserva> findReservaByAbonadoId(Long idAbonado);
}
