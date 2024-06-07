package com.polideportivo.springboot.backend.apirest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoPista.TipoPistaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;
import com.polideportivo.springboot.backend.apirest.models.services.ITipoPistaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tipo-pista")
@ControllerAdvice
public class TipoPistaRestController {

	@Autowired
	private ITipoPistaService tipoPistaService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@GetMapping("/lista")
	public ModelAndView listadoTipoPistasView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-tipo-pistas");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}
	
	@GetMapping("/modificar/{id}")
	public ModelAndView modificarTipoPistaView(@PathVariable Long id, Principal principal) {
		ModelAndView mav = new ModelAndView("modificar-tipo-pista");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("tipoPista", tipoPistaService.findById(id));
		mav.addObject("id", id);
		return mav;
	}
	
	@GetMapping("/crear")
	public ModelAndView crearTipoPistaView(Principal principal) {
		ModelAndView mav = new ModelAndView("crear-tipo-pista");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("tipoPista", new TipoPistaDto());
		return mav;
	}
	
	@PostMapping("/crear")
	private String add(@RequestBody @Valid TipoPistaDto tipoPista) {
		tipoPistaService.save(tipoPista);
		return "redirect:/tipo-pista/lista";
	}
	
	@PostMapping("/modificar/{id}")
	private String update(@RequestBody TipoPistaDto tipoPista, @PathVariable Long id) {
		tipoPistaService.update(tipoPista, id);
		return "redirect:/tipo-pista/lista";
	}
	
	@DeleteMapping("/eliminar/{id}")
	private void delete(@PathVariable Long id) {
		this.tipoPistaService.delete(id);
	}
}
