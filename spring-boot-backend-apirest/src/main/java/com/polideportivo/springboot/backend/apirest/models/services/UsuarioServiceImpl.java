package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.mapper.UsuarioMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.IRolDao;
import com.polideportivo.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private BCryptPasswordEncoder encriptador;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Override
	public UsuarioDto findById(String nombreUsuario) {
		// TODO Auto-generated method stub
		Optional<Usuario> optionalUsuario = usuarioDao.findById(nombreUsuario);
		if(optionalUsuario.isPresent()) {
			UsuarioDto usuarioResponseDto = usuarioMapper.entityToDto(optionalUsuario.get());
			return usuarioResponseDto;
		}
		else {
			throw new IllegalArgumentException("No existe usuario con el nombre de usuario: " + nombreUsuario);
		}
	}

	@Override
	public UsuarioDto save(UsuarioDto usuarioRequestDto) {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioRequestDto);
		usuario.setPassword(encriptador.encode(usuario.getPassword()));
		usuario.setRol(rolDao.findById(usuarioRequestDto.getIdRol()).get());
		usuarioDao.save(usuario);
		UsuarioDto usuarioResponseDto = usuarioMapper.entityToDto(usuario);
		return usuarioResponseDto;
	}

	@Override
	public UsuarioDto update(UsuarioDto usuarioRequestDto, String username) {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioRequestDto);
		usuarioDao.save(usuario);
		UsuarioDto usuarioResponseDto = usuarioMapper.entityToDto(usuario);
		return usuarioResponseDto;
	}

	@Override
	public void delete(String nombreUsuario) {
		// TODO Auto-generated method stub
		UsuarioDto usuarioResponseDto = this.findById(nombreUsuario);
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioResponseDto);
		usuarioDao.delete(usuario);
	}

	@Override
	public UsuarioDto findByUsuarioAndContrasena(String nombreUsuario, String contrasena) {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioDao.findUsuarioByUsuarioAndContrasena(nombreUsuario, encriptador.encode(contrasena));
		UsuarioDto usuarioResponseDto = usuarioMapper.entityToDto(usuario);
		return usuarioResponseDto;
	}

	@Override
	public ModelAndView loginUsuario(Usuario usuario, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ModelAndView mav;
		Optional<Usuario> optionalUsuario = usuarioDao.findById(usuario.getUsername());
		if(optionalUsuario.isPresent()) {
			Usuario miUsuario = optionalUsuario.get();
			if(encriptador.matches(usuario.getPassword(), miUsuario.getPassword())) {
				HttpSession sesion = request.getSession();
				sesion.setAttribute("usuario", miUsuario);
				mav = new ModelAndView("redirect:/tipo-hora/lista");
			}
			else {
				mav = new ModelAndView("redirect:/login");
				mav.addObject("mensaje", "Usuario o contraseña incorrectos");
			}
		}
		else {
			mav = new ModelAndView("redirect:/login");
			mav.addObject("mensaje", "Usuario no encontrado");
		}
		return mav;
	}

	@Override
	public ModelAndView registroUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		ModelAndView mav;
		Optional<Usuario> optionalUsuario = usuarioDao.findById(usuario.getUsername());
		if(!optionalUsuario.isPresent()) {
			usuario.setRol(rolDao.findById((long) 3).get());
			usuario.setPassword(encriptador.encode(usuario.getPassword()));
			usuarioDao.save(usuario);
			mav = new ModelAndView("redirect:/login");
			mav.addObject("mensaje", "Se ha registrado al usuario con éxito");
		}
		else {
			mav = new ModelAndView("redirect:/registro");
			mav.addObject("mensaje", "El usuario ya existe");
		}
		
		return mav;
	}
	
	@Override
    public ModelAndView logout(HttpServletRequest request) {
 
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

	@Override
	public List<UsuarioDto> findAll() {
		// TODO Auto-generated method stub
		List<Usuario> usuarioList = usuarioDao.findAll();
		if(usuarioList.isEmpty()) {
			return new ArrayList<UsuarioDto>();
		}
		else {
			List<UsuarioDto> usuarioDtoList = new ArrayList<>();
			for (Usuario usuario : usuarioList) {
				UsuarioDto usuarioDto = usuarioMapper.entityToDto(usuario);
				usuarioDto.setIdRol(usuario.getRol().getId());
				usuarioDtoList.add(usuarioDto);
			}
			return usuarioDtoList;
		}
	}

	@Override
	public boolean existeUsuario(String username) {
		// TODO Auto-generated method stub
		return usuarioDao.existsById(username);
	}

}
