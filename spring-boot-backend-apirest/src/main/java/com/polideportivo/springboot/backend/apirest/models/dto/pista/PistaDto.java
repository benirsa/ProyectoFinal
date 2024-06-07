package com.polideportivo.springboot.backend.apirest.models.dto.pista;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PistaDto {

	private Long id;

	@NotNull(message = "El tipo de pista no puede ser nulo")
	private Long idTipoPista;

	@NotNull(message = "El estado de la pista no puede ser nulo")
	@Size(max = 13, message = "El estado de la pista ha de tener un máximo de 13 caracteres")
	private String estado;

	@NotNull(message = "El techado de la pista no puede ser nulo")
	private Boolean techado;
}
