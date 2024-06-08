package com.polideportivo.springboot.backend.apirest.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polideportivo.springboot.backend.apirest.mapper.AbonadoMapper;
import com.polideportivo.springboot.backend.apirest.mapper.PistaMapper;
import com.polideportivo.springboot.backend.apirest.mapper.ReservaMapper;
import com.polideportivo.springboot.backend.apirest.mapper.TipoHoraMapper;
import com.polideportivo.springboot.backend.apirest.mapper.TipoPistaMapper;
import com.polideportivo.springboot.backend.apirest.mapper.UsuarioMapper;
import com.polideportivo.springboot.backend.apirest.models.dao.IReservaDao;
import com.polideportivo.springboot.backend.apirest.models.dao.IRolDao;
import com.polideportivo.springboot.backend.apirest.models.dto.abonado.AbonadoDto;
import com.polideportivo.springboot.backend.apirest.models.dto.pista.PistaDto;
import com.polideportivo.springboot.backend.apirest.models.dto.reserva.ReservaDto;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoHora.TipoHoraDto;
import com.polideportivo.springboot.backend.apirest.models.dto.tipoPista.TipoPistaDto;
import com.polideportivo.springboot.backend.apirest.models.dto.usuario.UsuarioDto;
import com.polideportivo.springboot.backend.apirest.models.entity.Abonado;
import com.polideportivo.springboot.backend.apirest.models.entity.Pista;
import com.polideportivo.springboot.backend.apirest.models.entity.Reserva;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoHora;
import com.polideportivo.springboot.backend.apirest.models.entity.TipoPista;
import com.polideportivo.springboot.backend.apirest.models.entity.Usuario;

@Service
public class ReservaServiceImpl implements IReservaService{
	
	@Autowired
	private IReservaDao reservaDao;
	
	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private IPistaService pistaService;
	
	@Autowired
	private IAbonadoService abonadoService;
	
	@Autowired
	private ITipoHoraService tipoHoraService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ITipoPistaService tipoPistaService;
	
	@Autowired
	private ReservaMapper reservaMapper;
	
	@Autowired
	private AbonadoMapper abonadoMapper;
	
	@Autowired
	private PistaMapper pistaMapper;
	
	@Autowired
	private TipoHoraMapper horaMapper;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Autowired
	private TipoPistaMapper tipoPistaMapper;

	@Override
	@Transactional(readOnly=true)
	public List<ReservaDto> findAll() {
		// TODO Auto-generated method stub
		List<Reserva> reservaList = reservaDao.findAll();
		if(reservaList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<ReservaDto> reservaDtoList = new ArrayList<>();
			for (Reserva reserva : reservaList) {
				ReservaDto reservaDto = reservaMapper.entityToDto(reserva);
				reservaDto.setIdAbonado(reserva.getAbonado().getId());
				reservaDto.setIdPista(reserva.getPista().getId());
				reservaDto.setIdTipoHora(reserva.getTipoHora().getId());
				reservaDtoList.add(reservaDto);
			}
			return reservaDtoList;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public ReservaDto findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Reserva> optionalReserva = reservaDao.findById(id);
		if(optionalReserva.isPresent()) {
			ReservaDto reservaResponseDto = reservaMapper.entityToDto(optionalReserva.get());
			return reservaResponseDto;
		}
		else {
			throw new IllegalArgumentException("No existe una reserva con el id: " + id);
		}
	}

	@Override
	@Transactional
	public ReservaDto save(ReservaDto reservaDto) {
		// TODO Auto-generated method stub
		Reserva reserva = reservaMapper.dtoToEntity(reservaDto);
		TipoHoraDto tipoHoraDto = tipoHoraService.findById(reservaDto.getIdTipoHora());
		TipoHora tipoHora = horaMapper.dtoToEntity(tipoHoraDto);
		PistaDto pistaDto = pistaService.findById(reservaDto.getIdPista());
		Pista pista = pistaMapper.dtoToEntity(pistaDto);
		TipoPistaDto tipoPistaDto = tipoPistaService.findById(pistaDto.getIdTipoPista());
		TipoPista tipoPista = tipoPistaMapper.dtoToEntity(tipoPistaDto);
		pista.setTipoPista(tipoPista);
		AbonadoDto abonadoDto = abonadoService.findById(reservaDto.getIdAbonado());
		Abonado abonado = abonadoMapper.dtoToEntity(abonadoDto);
		UsuarioDto usuarioDto = usuarioService.findById(abonadoDto.getUsername());
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
		usuario.setRol(rolDao.findById((long) 3).get());
		abonado.setUsuario(usuario);
		reserva.setAbonado(abonado);
		reserva.setPista(pista);
		reserva.setTipoHora(tipoHora);
		reserva.setPrecioReserva(this.calcularPrecioReserva(reserva));
		List<Reserva> reservas = reservaDao.findReservaByFechaReservaAndPistaId(reserva.getFechaReserva(), reserva.getPista().getId());
		for (Reserva reservada : reservas) {
			if(reservada.getTipoHora().getTramo() == tipoHora.getTramo()) {
				throw new IllegalArgumentException("La pista se encuentra reservada a esa hora"); 
			}
		}
		reservaDao.save(reserva);
		ReservaDto reservaResponseDto = reservaMapper.entityToDto(reserva);
		return reservaResponseDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ReservaDto reservaResponseDto = this.findById(id);
		Reserva reserva = reservaMapper.dtoToEntity(reservaResponseDto);
		reservaDao.delete(reserva);
	}

	@Override
	@Transactional
	public ReservaDto update(ReservaDto reservaDto, Long id) {
		System.out.println(reservaDto);
		// TODO Auto-generated method stub
		Reserva reserva = reservaMapper.dtoToEntity(reservaDto);
		TipoHoraDto tipoHoraDto = tipoHoraService.findById(reservaDto.getIdTipoHora());
		TipoHora tipoHora = horaMapper.dtoToEntity(tipoHoraDto);
		PistaDto pistaDto = pistaService.findById(reservaDto.getIdPista());
		Pista pista = pistaMapper.dtoToEntity(pistaDto);
		TipoPistaDto tipoPistaDto = tipoPistaService.findById(pistaDto.getIdTipoPista());
		TipoPista tipoPista = tipoPistaMapper.dtoToEntity(tipoPistaDto);
		pista.setTipoPista(tipoPista);
		AbonadoDto abonadoDto = abonadoService.findById(reservaDto.getIdAbonado());
		Abonado abonado = abonadoMapper.dtoToEntity(abonadoDto);
		UsuarioDto usuarioDto = usuarioService.findById(abonadoDto.getUsername());
		Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
		usuario.setRol(rolDao.findById((long) 3).get());
		abonado.setUsuario(usuario);
		reserva.setId(id);
		reserva.setAbonado(abonado);
		reserva.setPista(pista);
		reserva.setTipoHora(tipoHora);
		reserva.setPrecioReserva(this.calcularPrecioReserva(reserva));
		System.out.println(reserva);
		List<Reserva> reservas = reservaDao.findReservaByFechaReservaAndPistaId(reserva.getFechaReserva(), id);
		for (Reserva reservada : reservas) {
			if(reservada.getTipoHora().getTramo() == tipoHora.getTramo()) {
				throw new IllegalArgumentException("La pista se encuentra reservada a esa hora"); 
			}
		}
		reservaDao.save(reserva);
		ReservaDto reservaResponseDto = reservaMapper.entityToDto(reserva);
		return reservaResponseDto;
	}

	@Override
	public Double calcularPrecioReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		Double precio = reserva.getPista().getTipoPista().getPrecio();
		
		Abonado abonado = reserva.getAbonado();
		long numReservas = reservaDao.findCountByAbonadoDni(abonado.getDni()) + 1;
		
		// si ha hecho un multiplo de 5 reservas se aplica un descuento del 15% a la reserva de la pista
		if(numReservas % 5 == 0) {
			// si ha comitido 3 faltas, no se aplica el descuento
			if(abonado.getFaltas() == 3) {
				abonado.setFaltas(0);
			}
			else {
				precio *= 0.15;
			}
		}
		
		// redondeamos el precio a dos decimales
		precio = Math.round(precio * 100.0) / 100.0;
		
		return precio;
	}

	@Override
	public List<ReservaDto> findAllByAbonadoId(Long id) {
		// TODO Auto-generated method stub
		List<Reserva> reservaList = reservaDao.findReservaByAbonadoId(id);
		if(reservaList.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			List<ReservaDto> reservaDtoList = new ArrayList<>();
			for (Reserva reserva : reservaList) {
				ReservaDto reservaDto = reservaMapper.entityToDto(reserva);
				reservaDto.setIdAbonado(reserva.getAbonado().getId());
				reservaDto.setIdPista(reserva.getPista().getId());
				reservaDto.setIdTipoHora(reserva.getTipoHora().getId());
				reservaDtoList.add(reservaDto);
			}
			return reservaDtoList;
		}
	}
}
