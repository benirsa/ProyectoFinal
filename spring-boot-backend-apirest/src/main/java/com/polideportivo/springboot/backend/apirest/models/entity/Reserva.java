package com.polideportivo.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@ToString
public class Reserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9123359604411345146L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_pista")
	private Pista pista;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_tipo_hora")
	private TipoHora tipoHora;

	@Column(name = "fecha_reserva")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date fechaReserva;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_abonado")
	private Abonado abonado;

	@Column(name="precio_reserva")
	private Double precioReserva;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
}
