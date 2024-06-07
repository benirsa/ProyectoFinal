package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polideportivo.springboot.backend.apirest.mapper.TrabajadorMapper;
import com.polideportivo.springboot.backend.apirest.mapper.UsuarioMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.IRolDao;
import com.polideportivo.springboot.backend.apirest.models.dao.ITrabajadorDao;
import com.polideportivo.springboot.backend.apirest.models.dto.trabajador.TrabajadorDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Trabajador;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService{

	@Autowired
	private ITrabajadorDao trabajadorDao;
	
	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private TrabajadorMapper trabajadorMapper;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<TrabajadorDto> findAll() {
		// TODO Auto-generated method stub
		List<Trabajador> usuarioList = trabajadorDao.findAll();
		if(usuarioList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<TrabajadorDto> usuarioResponseDtoList = trabajadorMapper.entityListToDtoList(usuarioList);
			return usuarioResponseDtoList;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public TrabajadorDto findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Trabajador> optionalTrabajador = trabajadorDao.findById(id);
		if(optionalTrabajador.isPresent()) {
			TrabajadorDto trabajadorDto = trabajadorMapper.entityToDto(optionalTrabajador.get());
			trabajadorDto.setUsername(optionalTrabajador.get().getUsuario().getUsername());
			trabajadorDto.setPassword(optionalTrabajador.get().getUsuario().getPassword());
			trabajadorDto.setIdRol(optionalTrabajador.get().getUsuario().getRol().getId());
			return trabajadorDto;
		}
		else {
			throw new IllegalArgumentException("No existe un usuario con el id: " + id);
		}
	}

	@Override
	@Transactional
	public TrabajadorDto save(TrabajadorDto trabajadorDto) {
		// TODO Auto-generated method stub
		Usuario usuario = new Usuario();
		usuario.setUsername(trabajadorDto.getUsername());
		usuario.setPassword(trabajadorDto.getPassword());
		usuario.setRol(rolDao.findById(trabajadorDto.getIdRol()).get());
		UsuarioDto usuarioDto = usuarioMapper.entityToDto(usuario);
		usuarioDto.setIdRol(usuario.getRol().getId());
		usuarioService.save(usuarioDto);
		Trabajador trabajador = trabajadorMapper.dtoToEntity(trabajadorDto);
		trabajador.setUsuario(usuario);
		trabajadorDao.save(trabajador);
		TrabajadorDto trabajadorDtoAux = trabajadorMapper.entityToDto(trabajador);
		trabajadorDtoAux.setUsername(trabajador.getUsuario().getUsername());
		trabajadorDtoAux.setPassword(trabajador.getUsuario().getPassword());
		trabajadorDtoAux.setIdRol(trabajador.getUsuario().getRol().getId());
		return trabajadorDtoAux;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		TrabajadorDto trabajadorResponseDto = this.findById(id);
		Trabajador trabajador = trabajadorMapper.dtoToEntity(trabajadorResponseDto);
		trabajadorDao.delete(trabajador);
	}

	@Override
	@Transactional
	public TrabajadorDto update(TrabajadorDto trabajadorDto, Long id) {
		// TODO Auto-generated method stub
		Trabajador trabajador = trabajadorMapper.dtoToEntity(trabajadorDto);
		trabajador.setId(id);
		UsuarioDto usuarioDto = usuarioService.findById(trabajadorDto.getUsername());
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
		usuario.setRol(rolDao.findById(trabajadorDto.getIdRol()).get());
		trabajador.setUsuario(usuario);
		trabajadorDao.save(trabajador);
		TrabajadorDto trabajadorDtoAux = trabajadorMapper.entityToDto(trabajador);
		trabajadorDtoAux.setUsername(trabajador.getUsuario().getUsername());
		trabajadorDtoAux.setPassword(trabajador.getUsuario().getPassword());
		trabajadorDtoAux.setIdRol(trabajador.getUsuario().getRol().getId());
		return trabajadorDtoAux;
	}
	
	
}
