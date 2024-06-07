package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.polideportivo.springboot.backend.apirest.mapper.AbonadoMapper;
import com.polideportivo.springboot.backend.apirest.mapper.UsuarioMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.IAbonadoDao;
import com.polideportivo.springboot.backend.apirest.models.dao.IRolDao;
import com.polideportivo.springboot.backend.apirest.models.dto.abonado.AbonadoDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Abonado;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Service
public class AbonadoServiceImpl implements IAbonadoService{

	@Autowired
	private IAbonadoDao abonadoDao;
	
	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private AbonadoMapper abonadoMapper;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<AbonadoDto> findAll() {
		// TODO Auto-generated method stub
		List<Abonado> abonadoList = abonadoDao.findAll();
		if(abonadoList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<AbonadoDto> abonadoDtoList = new ArrayList<>();
			for (Abonado abonado : abonadoList) {
				AbonadoDto abonadoDto = abonadoMapper.entityToDto(abonado);
				abonadoDto.setUsername(abonado.getUsuario().getUsername());
				abonadoDto.setPassword(abonado.getUsuario().getPassword());
				abonadoDto.setIdRol((long) 3);
				abonadoDtoList.add(abonadoDto);
			}
			return abonadoDtoList;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public AbonadoDto findById(Long id) {
		// TODO Auto-generated method stub
		 Optional<Abonado> optionalAbonado = abonadoDao.findById(id);
		 if(optionalAbonado.isPresent()) {
			 AbonadoDto abonadoDto = abonadoMapper.entityToDto(optionalAbonado.get());
				abonadoDto.setUsername(optionalAbonado.get().getUsuario().getUsername());
				abonadoDto.setPassword(optionalAbonado.get().getUsuario().getPassword());
				abonadoDto.setIdRol((long) 3);
			 return abonadoDto;
		 }
		 else {
			 throw new IllegalArgumentException("No existe un abonado con el id: " + id);
		 }
	}

	@Override
	@Transactional
	public ModelAndView save(AbonadoDto abonadoDto) {
		// TODO Auto-generated method stub
		ModelAndView mav;
		Abonado abonado = abonadoMapper.dtoToEntity(abonadoDto);
		Usuario usuario = new Usuario();
		usuario.setUsername(abonadoDto.getUsername());
		usuario.setPassword(abonadoDto.getPassword());
		usuario.setRol(rolDao.findById((long) 3).get());
		UsuarioDto usuarioDto = usuarioMapper.entityToDto(usuario);
		usuarioDto.setIdRol((long) 3);
		usuarioService.save(usuarioDto);
		System.out.println(usuario);
		abonado.setUsuario(usuario);
		abonado.setId(null);
		abonadoDao.save(abonado);
		
		mav = new ModelAndView("redirect:/abonados/lista");
		mav.addObject("abonados", this.findAll());
		return mav;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		AbonadoDto abonadoResponseDto = this.findById(id);
		Abonado abonado = abonadoMapper.dtoToEntity(abonadoResponseDto);
		abonadoDao.delete(abonado);
	}

	@Override
	@Transactional
	public AbonadoDto update(AbonadoDto abonadoDto, Long id) {
		// TODO Auto-generated method stub
		Abonado abonado = abonadoMapper.dtoToEntity(abonadoDto);
		UsuarioDto usuarioDto = usuarioService.findById(abonadoDto.getUsername());
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
		abonado.setId(id);
		abonado.setUsuario(usuario);
		abonadoDao.save(abonado);
		AbonadoDto abonadoResponseDto = abonadoMapper.entityToDto(abonado);
		return abonadoResponseDto;
	}

}
