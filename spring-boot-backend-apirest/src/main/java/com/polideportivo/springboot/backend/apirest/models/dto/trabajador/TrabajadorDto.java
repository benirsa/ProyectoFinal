package com.polideportivo.springboot.backend.apirest.models.dto.trabajador;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrabajadorDto {
	
	private Long id;

	@NotNull(message = "El dni no puede ser nulo")
	@Size(min = 9, max = 9, message = "El dni ha de tener 9 caracteres")
	private String dni;
	
	@NotNull(message = "El nombre no puede ser nulo")
	@Size(max = 50, message = "El nombre ha de tener 50 caracteres")
	private String nombre;
	
	@NotNull(message = "El apellido 1 no puede ser nulo")
	@Size(max = 50, message = "El apellido 1 ha de tener 50 caracteres")
	private String apellido1;
	
	@NotNull(message = "El apellido 2 no puede ser nulo")
	@Size(max = 50, message = "El apellido 2 ha de tener 50 caracteres")
	private String apellido2;
	
	@NotNull(message = "La fecha de nacimiento no puede ser nulo")
	private Date fechaNacimiento;
	
	@NotNull(message = "El número de teléfono no puede ser nulo")
	@Size(min = 9, max = 9, message = "El número de teléfono ha de tener 9 caracteres")
	private String telefono;
	
	@NotNull(message = "El usuario no puede ser nulo")
	private String username;
	
	private String password;
	
	private Long idRol;
}
