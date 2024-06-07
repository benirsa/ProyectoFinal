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
import com.polideportivo.springboot.backend.apirest.models.dto.abonado.AbonadoDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;
import com.polideportivo.springboot.backend.apirest.models.services.IAbonadoService;

@Controller
@RequestMapping("/abonados")
@ControllerAdvice
public class AbonadoRestController {

	@Autowired
	private IAbonadoService abonadoService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@GetMapping("/lista")
	public ModelAndView listadoAbonadosView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-abonados");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("abonados", abonadoService.findAll());
		return mav;
	}
	
	@GetMapping("/modificar/{id}")
	public ModelAndView modificarAbonadoView(@PathVariable Long id, Principal principal) {
		ModelAndView mav = new ModelAndView("modificar-abonado");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("abonado", abonadoService.findById(id));
		mav.addObject("id", id);
		return mav;
	}
	
	@GetMapping("/crear")
	public ModelAndView crearAbonadoView(Principal principal) {
		ModelAndView mav = new ModelAndView("crear-abonado");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("abonado", new AbonadoDto());
		return mav;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody AbonadoDto abonado) {
		abonadoService.save(abonado);
		return "redirect:/abonados/lista";
	}
	
	@PostMapping("/modificar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String update(@RequestBody AbonadoDto abonado, @PathVariable Long id) {
		abonadoService.update(abonado, id);
		return "redirect:/abonados/lista";
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		abonadoService.delete(id);
	}
}
