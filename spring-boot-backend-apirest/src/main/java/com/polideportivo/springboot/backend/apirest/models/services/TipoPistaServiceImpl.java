package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polideportivo.springboot.backend.apirest.mapper.TipoPistaMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.ITipoPistaDao;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoPista.TipoPistaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoPista;

@Service
public class TipoPistaServiceImpl implements ITipoPistaService{

	@Autowired
	private ITipoPistaDao pistaDao;
	
	@Autowired
	private TipoPistaMapper pistaMapper;

	@Override
	@Transactional(readOnly=true)
	public List<TipoPistaDto> findAll() {
		// TODO Auto-generated method stub
		List<TipoPista> tipoPistaList = pistaDao.findAll();
		if(tipoPistaList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<TipoPistaDto> tipoPistaResponseDtoList = pistaMapper.entityListToDtoList(tipoPistaList);
			return tipoPistaResponseDtoList;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public TipoPistaDto findById(Long id) {
		// TODO Auto-generated method stub
		Optional<TipoPista> optionalPista = pistaDao.findById(id);
		if(optionalPista.isPresent()) {
			TipoPistaDto tipoPistaResponseDto = pistaMapper.entityToDto(optionalPista.get());
			return tipoPistaResponseDto;
		}
		else {
			throw new IllegalArgumentException("No existe un tipo de pista con el id: " + id);
		}
	}

	@Override
	@Transactional
	public TipoPistaDto save(TipoPistaDto TipoPistaRequestDto) {
		// TODO Auto-generated method stub
		TipoPista pista = pistaMapper.dtoToEntity(TipoPistaRequestDto);
		pistaDao.save(pista);
		TipoPistaDto tipoPistaResponseDto = pistaMapper.entityToDto(pista);
		return tipoPistaResponseDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		TipoPistaDto tipoPistaResponseDto = this.findById(id);
		TipoPista pista = pistaMapper.dtoToEntity(tipoPistaResponseDto);
		pistaDao.delete(pista);
	}

	@Override
	@Transactional
	public TipoPistaDto update(TipoPistaDto TipoPistaRequestDto, Long id) {
		// TODO Auto-generated method stub
		TipoPista pista = pistaMapper.dtoToEntity(TipoPistaRequestDto);
		pista.setId(id);
		pistaDao.save(pista);
		TipoPistaDto tipoPistaResponseDto = pistaMapper.entityToDto(pista);
		return tipoPistaResponseDto;
	}
}
