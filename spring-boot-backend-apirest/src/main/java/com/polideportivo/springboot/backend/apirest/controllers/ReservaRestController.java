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

import com.polideportivo.springboot.backend.apirest.models.dao.IAbonadoDao;
import com.polideportivo.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.polideportivo.springboot.backend.apirest.models.dto.reserva.ReservaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Abonado;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;
import com.polideportivo.springboot.backend.apirest.models.services.IAbonadoService;
import com.polideportivo.springboot.backend.apirest.models.services.IPistaService;
import com.polideportivo.springboot.backend.apirest.models.services.IReservaService;
import com.polideportivo.springboot.backend.apirest.models.services.ITipoHoraService;
import com.polideportivo.springboot.backend.apirest.models.services.ITipoPistaService;

@Controller
@RequestMapping("/reservas")
@ControllerAdvice
public class ReservaRestController {

	@Autowired
	private IReservaService reservaService;

	@Autowired
	private IAbonadoDao abonadoDao;

	@Autowired
	private IPistaService pistaService;

	@Autowired
	private IAbonadoService abonadoService;

	@Autowired
	private ITipoHoraService tipoHoraService;

	@Autowired
	private ITipoPistaService tipoPistaService;
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@GetMapping("/lista")
	public ModelAndView listadoReservasView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-reservas");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("reservas", reservaService.findAll());
		mav.addObject("pistas", pistaService.findAll());
		mav.addObject("horas", tipoHoraService.findAll());
		mav.addObject("abonados", abonadoService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}

	@GetMapping("/lista/abonado")
	public ModelAndView listadoReservasAbonadoView(Principal principal) {
		ModelAndView mav = new ModelAndView("listado-reservas-abonado");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		Abonado abonado = abonadoDao.findAbonadoByUsername(username);
		mav.addObject("reservas", reservaService.findAllByAbonadoId(abonado.getId()));
		mav.addObject("pistas", pistaService.findAll());
		mav.addObject("horas", tipoHoraService.findAll());
		mav.addObject("abonados", abonadoService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}

	@GetMapping("/modificar/{id}")
	public ModelAndView modificarReservaView(@PathVariable Long id, Principal principal) {
		ModelAndView mav = new ModelAndView("modificar-reserva");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("reserva", reservaService.findById(id));
		mav.addObject("pistas", pistaService.findByEstado("ACTIVA"));
		mav.addObject("abonados", abonadoService.findAll());
		mav.addObject("horas", tipoHoraService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		mav.addObject("id", id);
		return mav;
	}
	
	@GetMapping("/modificar/abonado/{id}")
	public ModelAndView modificarReservaAbonadoView(@PathVariable Long id, Principal principal) {
		ModelAndView mav = new ModelAndView("modificar-reserva-abonado");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("reserva", reservaService.findById(id));
		mav.addObject("pistas", pistaService.findByEstado("ACTIVA"));
		mav.addObject("abonado", abonadoDao.findAbonadoByUsername(username));
		mav.addObject("horas", tipoHoraService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		mav.addObject("id", id);
		return mav;
	}
	
	@GetMapping("/crear/abonado")
	public ModelAndView crearReservaAbonadoView(Principal principal) {
		ModelAndView mav = new ModelAndView("crear-reserva-abonado");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("reserva", new ReservaDto());
		mav.addObject("pistas", pistaService.findByEstado("ACTIVA"));
		mav.addObject("abonado", abonadoDao.findAbonadoByUsername(username));
		mav.addObject("horas", tipoHoraService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}

	@GetMapping("/crear")
	public ModelAndView crearReservaView(Principal principal) {
		ModelAndView mav = new ModelAndView("crear-reserva");
		String username = principal.getName();
		Usuario usuario = usuarioDao.findById(username).get();
		mav.addObject("rol", usuario.getRol().getNombre());
		mav.addObject("reserva", new ReservaDto());
		mav.addObject("pistas", pistaService.findByEstado("ACTIVA"));
		mav.addObject("abonados", abonadoService.findAll());
		mav.addObject("horas", tipoHoraService.findAll());
		mav.addObject("tipoPistas", tipoPistaService.findAll());
		return mav;
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody ReservaDto abonado) {
		reservaService.save(abonado);
		return "redirect:/abonados/lista";
	}

	@PostMapping("/modificar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String update(@RequestBody ReservaDto reserva, @PathVariable Long id) {
		reservaService.update(reserva, id);
		return "redirect:/abonados/lista";
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		reservaService.delete(id);
	}
}
