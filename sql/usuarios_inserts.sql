-- Las contraseñas están cifradas, para iniciar sesión, introduca contraUsuario por ejemplo contraAdmin, esto es así
-- para todos los usuarios excepto para el trabajador que su contraseña es trabajador
INSERT INTO public.usuarios (usuario,contrasena,id_rol) VALUES
	 ('pedro','$2a$10$hyzmoWvPrD6tUrTuwvhuz.wpmfgqWBG2wQfPquALlO2h0UFyUBBUm',3),
	 ('luis','$2a$10$1oO0T6Fp/YQehyhvasYoBusrDe7bCgGXz2Xi6dlK8g1VCvySDS3bW',3),
	 ('carlos','$2a$10$BsAgxyTfoTgoSSX3hKkqF.lq6.6DNJwZBwRI7XLR5OPJETw2QSYHW',3),
	 ('ivan','$2a$10$8LwgnNK6wNb199zefhoCu.U5upa35X6QPErypSKlvi/cr32GvM/6.',3),
	 ('beni','$2a$10$YWLsT8RxLEYp9p8tDruR5eudC..PkAn7suumgfgFq3ltLRkP41SzS',3),
	 ('admin','$2a$10$jcD/0V9VTzi86gzFHtKOu.ufD13fvp3ALDanN3hUKlmCWKo6vonjG',1),
	 ('trabajador','$2a$10$sMNrhRtqgCgTUfu6O86NyO.jQRHYDHO0wQW5UKFe54s3zQEKxD1rO',2),;
