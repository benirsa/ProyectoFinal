-- public.roles definition

-- Drop table

-- DROP TABLE public.roles;

CREATE TABLE public.roles (
	id serial4 NOT NULL,
	nombre varchar(10) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
);


-- public.tipo_hora definition

-- Drop table

-- DROP TABLE public.tipo_hora;

CREATE TABLE public.tipo_hora (
	id serial4 NOT NULL,
	tramo varchar(11) NULL,
	CONSTRAINT tipo_hora_pk PRIMARY KEY (id)
);


-- public.tipo_pista definition

-- Drop table

-- DROP TABLE public.tipo_pista;

CREATE TABLE public.tipo_pista (
	id serial4 NOT NULL,
	descripcion varchar(50) NULL,
	precio float4 NULL,
	CONSTRAINT tipo_pista_pk PRIMARY KEY (id)
);


-- public.usuarios definition

-- Drop table

-- DROP TABLE public.usuarios;

CREATE TABLE public.usuarios (
	usuario varchar(16) NOT NULL,
	contrasena text NOT NULL,
	id_rol int4 NOT NULL,
	CONSTRAINT usuarios_pk PRIMARY KEY (usuario),
	CONSTRAINT usuarios_roles_fk FOREIGN KEY (id_rol) REFERENCES public.roles(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- public.abonados definition

-- Drop table

-- DROP TABLE public.abonados;

CREATE TABLE public.abonados (
	id serial4 NOT NULL,
	dni varchar(9) NOT NULL,
	nombre varchar(50) NULL,
	apellido1 varchar(50) NULL,
	apellido2 varchar(50) NULL,
	fecha_nacimiento date NULL,
	telefono varchar(9) NULL,
	direccion varchar(150) NULL,
	create_at date NULL,
	faltas int4 NOT NULL,
	usuario varchar(16) NOT NULL,
	CONSTRAINT abonados_pk PRIMARY KEY (id),
	CONSTRAINT abonados_usuarios_fk FOREIGN KEY (usuario) REFERENCES public.usuarios(usuario) ON DELETE CASCADE ON UPDATE CASCADE
);


-- public.pistas definition

-- Drop table

-- DROP TABLE public.pistas;

CREATE TABLE public.pistas (
	id serial4 NOT NULL,
	id_tipo_pista int4 NULL,
	estado varchar(13) NULL,
	techado bool NULL,
	create_at date NULL,
	CONSTRAINT pistas_pk PRIMARY KEY (id),
	CONSTRAINT pistas_tipo_pista_fk FOREIGN KEY (id_tipo_pista) REFERENCES public.tipo_pista(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- public.reservas definition

-- Drop table

-- DROP TABLE public.reservas;

CREATE TABLE public.reservas (
	id serial4 NOT NULL,
	id_pista int4 NOT NULL,
	id_tipo_hora int4 NOT NULL,
	id_abonado int4 NULL,
	precio_reserva float4 NULL,
	create_at date NULL,
	fecha_reserva date NULL,
	CONSTRAINT reservas_pk PRIMARY KEY (id),
	CONSTRAINT reservas_abonados_fk FOREIGN KEY (id_abonado) REFERENCES public.abonados(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT reservas_pistas_fk FOREIGN KEY (id_pista) REFERENCES public.pistas(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT reservas_tipo_hora_fk FOREIGN KEY (id_tipo_hora) REFERENCES public.tipo_hora(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- public.trabajadores definition

-- Drop table

-- DROP TABLE public.trabajadores;

CREATE TABLE public.trabajadores (
	id serial4 NOT NULL,
	dni varchar(9) NOT NULL,
	nombre varchar(50) NULL,
	apellido1 varchar(50) NULL,
	apellido2 varchar(50) NULL,
	fecha_nacimiento date NULL,
	telefono varchar(9) NULL,
	create_at date NULL,
	usuario varchar(16) NOT NULL,
	CONSTRAINT trabajadores_pk PRIMARY KEY (id),
	CONSTRAINT trabajadores_usuarios_fk FOREIGN KEY (usuario) REFERENCES public.usuarios(usuario) ON DELETE CASCADE ON UPDATE CASCADE
);