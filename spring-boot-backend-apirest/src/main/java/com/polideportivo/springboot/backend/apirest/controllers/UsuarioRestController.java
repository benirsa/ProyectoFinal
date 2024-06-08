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

import com.polideportivo.springboot.backend.apirest.models.dao.IRolDao;
import com.polideportivo.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.polideportivo.springboot.backend.apirest.models.dto.trabajador.TrabajadorDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;
import com.polideportivo.springboot.backend.apirest.models.services.ITrabajadorService;
import com.polideportivo.springboot.backend.apirest.models.services.IUsuarioService;

@Controller
@RequestMapping("/trabajadores")
@ControllerAdvice
public class UsuarioRestController {

	@Autowired
	private ITrabajadorService trabajadorService;
	
	@Autowired 
	private IUsuarioService usuarioService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IRolDao rolDao;
	
	@GetMapping("/lista")
	public ModelAndView listadoTrabajadoresView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-trabajadores");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("trabajadores", trabajadorService.findAll());
		mav.addObject("usuario", usuarioService.findAll());
		return mav;
	}
	
	@GetMapping("/modificar/{id}")
	public ModelAndView modificarTrabajadorView(@PathVariable Long id, Principal principal) {
		ModelAndView mav = new ModelAndView("modificar-trabajador");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		TrabajadorDto trabajador = trabajadorService.findById(id);
		mav.addObject("trabajador", trabajador);
		mav.addObject("usuario", usuarioService.findById(trabajador.getUsername()));
		mav.addObject("roles", rolDao.findAll());
		mav.addObject("id", id);
		return mav;
	}
	
	@GetMapping("/crear")
	public ModelAndView crearTrabajadorView(Principal principal) {
		ModelAndView mav = new ModelAndView("crear-trabajador");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("trabajador", new TrabajadorDto());
		mav.addObject("usuario", new UsuarioDto());
		mav.addObject("roles", rolDao.findAll());
		return mav;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody TrabajadorDto trabajadorDto) {
		trabajadorService.save(trabajadorDto);
		return "redirect:/usuarios/lista";
	}
	
	@PostMapping("/modificar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String update(@RequestBody TrabajadorDto usuario, @PathVariable Long id) {
		trabajadorService.update(usuario, id);
		return "redirect:/usuarios/lista";
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		trabajadorService.delete(id);
	}
}
