package com.polideportivo.springboot.backend.apirest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoHora.TipoHoraDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;
import com.polideportivo.springboot.backend.apirest.models.services.ITipoHoraService;


@RestController
@RequestMapping("/tipo-hora")
@ControllerAdvice
public class TipoHoraRestController {

	@Autowired
	private ITipoHoraService horaService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@GetMapping("/lista")
	public ModelAndView listadoTipoHorasView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-tipo-horas");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("horas", horaService.findAll());
		return mav;
	}
	
	@GetMapping("/{id}")
	private TipoHoraDto show(@PathVariable Long id) {
		return horaService.findById(id);
	}
	
	@PostMapping("/crear")
	private TipoHoraDto create(@RequestBody TipoHoraDto tipoHora) {
		return horaService.save(tipoHora);
	}
	
	@PutMapping("/modificar/{id}")
	private TipoHoraDto update(@PathVariable Long id, @RequestParam TipoHoraDto tipoHora) {
		return horaService.update(tipoHora, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	private void delete(@PathVariable Long id) {
		horaService.delete(id);
	}
}
