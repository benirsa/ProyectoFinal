package com.polideportivo.springboot.backend.apirest.models.dto.tipoHora;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TipoHoraDto {

	private Long id;

	@NotNull(message = "El tramo horario no puede ser nulo")
	@Size(max = 11, message = "El tramo horario no puede contener más de 11 caracteres")
	private String tramo;
}
