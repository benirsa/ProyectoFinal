package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polideportivo.springboot.backend.apirest.mapper.TipoHoraMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.ITipoHoraDao;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoHora.TipoHoraDto;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoHora;

@Service
public class TipoHoraServiceImpl implements ITipoHoraService{
	
	@Autowired
	private ITipoHoraDao horaDao;
	
	@Autowired
	private TipoHoraMapper horaMapper;

	@Override
	@Transactional(readOnly=true)
	public List<TipoHoraDto> findAll() {
		// TODO Auto-generated method stub
		List<TipoHora> tipoHoraList = horaDao.findAll();
		if(tipoHoraList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<TipoHoraDto> tipoHoraResponseDtoList = horaMapper.entityListToDtoList(tipoHoraList);
			return tipoHoraResponseDtoList;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public TipoHoraDto findById(Long id) {
		// TODO Auto-generated method stub
		Optional<TipoHora> optionalHora = horaDao.findById(id);
		if(optionalHora.isPresent()) {
			TipoHoraDto tipoHoraResponseDto = horaMapper.entityToDto(optionalHora.get());
			return tipoHoraResponseDto;
		}
		else {
			throw new IllegalArgumentException("No existe una hora con el id: " + id);
		}
	}

	@Override
	@Transactional
	public TipoHoraDto save(TipoHoraDto tipoHora) {
		// TODO Auto-generated method stub
		TipoHora hora = horaMapper.dtoToEntity(tipoHora);
		horaDao.save(hora);
		TipoHoraDto tipoHoraResponseDto = horaMapper.entityToDto(hora);
		return tipoHoraResponseDto; 
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		TipoHoraDto tipoHoraResponseDto = this.findById(id);
		TipoHora hora = horaMapper.dtoToEntity(tipoHoraResponseDto);
		horaDao.delete(hora);
	}

	@Override
	@Transactional
	public TipoHoraDto update(TipoHoraDto tipoHora, Long id) {
		// TODO Auto-generated method stub
		TipoHora hora = horaMapper.dtoToEntity(tipoHora);
		hora.setId(id);
		horaDao.save(hora);
		TipoHoraDto tipoHoraResponseDto = horaMapper.entityToDto(hora);
		return tipoHoraResponseDto;
	}
	
	
}
