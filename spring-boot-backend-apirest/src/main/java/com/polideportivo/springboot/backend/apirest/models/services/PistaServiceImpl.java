package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polideportivo.springboot.backend.apirest.mapper.PistaMapper;
import com.polideportivo.springboot.backend.apirest.mapper.TipoPistaMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.IPistaDao;
import com.polideportivo.springboot.backend.apirest.models.dto.pista.PistaDto;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoPista.TipoPistaDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Pista;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoPista;

@Service
public class PistaServiceImpl implements IPistaService{
	
	@Autowired
	private IPistaDao pistaDao;
	
	@Autowired
	private ITipoPistaService tipoPistaService;
	
	@Autowired
	private PistaMapper pistaMapper;
	
	@Autowired
	private TipoPistaMapper tipoPistaMapper;

	@Override
	@Transactional(readOnly=true)
	public List<PistaDto> findAll() {
		// TODO Auto-generated method stub
		List<Pista> pistaList = pistaDao.findAll();
		if(pistaList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<PistaDto> pistaDtoList = new ArrayList<>();
			for (Pista pista : pistaList) {
				PistaDto pistaDto = pistaMapper.entityToDto(pista);
				pistaDto.setIdTipoPista(pista.getTipoPista().getId());
				pistaDtoList.add(pistaDto);
				
			}
			return pistaDtoList;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public PistaDto findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Pista> optionalPista = pistaDao.findById(id);
		if(optionalPista.isPresent()) {
			PistaDto pistaResponseDto = pistaMapper.entityToDto(optionalPista.get());
			pistaResponseDto.setIdTipoPista(optionalPista.get().getTipoPista().getId());
			return pistaResponseDto;
		}
		else {
			throw new IllegalArgumentException("No existe pista con el id: " + id);
		}
	}

	@Override
	@Transactional
	public PistaDto save(PistaDto pistaRequestDto) {
		// TODO Auto-generated method stub
		Pista pista = pistaMapper.dtoToEntity(pistaRequestDto);
		TipoPistaDto tipoPistaResponseDto = tipoPistaService.findById(pistaRequestDto.getIdTipoPista());
		TipoPista tipoPista = tipoPistaMapper.dtoToEntity(tipoPistaResponseDto);
		pista.setTipoPista(tipoPista);
		pistaDao.save(pista);
		PistaDto pistaResponseDto = pistaMapper.entityToDto(pista);
		return pistaResponseDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		PistaDto pistaResponsDto = this.findById(id);
		Pista pista = pistaMapper.dtoToEntity(pistaResponsDto);
		pistaDao.delete(pista);
	}

	@Override
	@Transactional
	public PistaDto update(PistaDto pistaRequestDto, Long id) {
		// TODO Auto-generated method stub
		Pista pista = pistaMapper.dtoToEntity(pistaRequestDto);
		pista.setId(id);
		TipoPistaDto tipoPistaResponseDto = tipoPistaService.findById(pistaRequestDto.getIdTipoPista());
		TipoPista tipoPista = tipoPistaMapper.dtoToEntity(tipoPistaResponseDto);
		pista.setTipoPista(tipoPista);
		pistaDao.save(pista);
		PistaDto pistaResponseDto = pistaMapper.entityToDto(pista);
		return pistaResponseDto;
	}

	@Override
	public List<PistaDto> findByEstado(String estado) {
		// TODO Auto-generated method stub
		List<Pista> pistaList = pistaDao.findByEstado(estado);
		if(pistaList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<PistaDto> pistaDtoList = new ArrayList<>();
			for (Pista pista : pistaList) {
				PistaDto pistaResponseDto = pistaMapper.entityToDto(pista);
				pistaResponseDto.setIdTipoPista(pista.getTipoPista().getId());
				pistaDtoList.add(pistaResponseDto);
			}
			return pistaDtoList;
		}
	}
	
	
}
