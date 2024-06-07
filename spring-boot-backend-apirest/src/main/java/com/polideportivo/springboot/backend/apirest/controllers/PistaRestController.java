package com.polideportivo.springboot.backend.apirest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.polideportivo.springboot.backend.apirest.models.dto.pista.PistaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;
import com.polideportivo.springboot.backend.apirest.models.services.IPistaService;
import com.polideportivo.springboot.backend.apirest.models.services.ITipoPistaService;

@Controller
@RequestMapping("/pistas")
@ControllerAdvice
public class PistaRestController {

	@Autowired
	private IPistaService pistaService;
	
	@Autowired
	private ITipoPistaService tipoPistaService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@GetMapping("/lista")
	public ModelAndView listadoPistasView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-pistas");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("pistas", pistaService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}
	
	@GetMapping("/lista/abonado")
	public ModelAndView listadoPistasAbonadoView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-pistas-abonado");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("pistas", pistaService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}
	
	@GetMapping("/modificar/{id}")
	public ModelAndView modificarPistaView(@PathVariable Long id, Principal principal) {
		ModelAndView mav = new ModelAndView("modificar-pista");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("pista", pistaService.findById(id));
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		mav.addObject("id", id);
		return mav;
	}
	
	@GetMapping("/crear")
	public ModelAndView crearPistaView(Principal principal) {
		ModelAndView mav = new ModelAndView("crear-pista");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("pista", new PistaDto());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody PistaDto pista) {
		pistaService.save(pista);
		return "redirect:/pistas/lista";
	}
	
	@PostMapping("/modificar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String update(@RequestBody PistaDto pista, @PathVariable Long id) {		
		pistaService.save(pista);
		return "redirect:/pistas/lista";
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		pistaService.delete(id);
	}
}
