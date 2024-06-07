package com.polideportivo.springboot.backend.apirest.models.dto.usuario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioDto {

	@NotNull(message = "El nombre de usuario no puede ser nulo")
	@Max(value = 16, message = "El nombre de usuario ha de tener más de 6 carácteres y menos de 16")
	@Min(value = 6, message = "El nombre de usuario ha de tener más de 6 carácteres y menos de 16")
	public String username;
	
	@NotNull(message = "La contraseña no puede ser nulo")
	public String password;

	public Long idRol;
}
