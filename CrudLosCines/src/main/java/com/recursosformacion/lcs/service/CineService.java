package com.recursosformacion.lcs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.repository.ICine;
import com.recursosformacion.lcs.service.interfaces.IServicio;
import com.recursosformacion.lcs.util.Rutinas;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;


@Service
@Validated
public class CineService implements IServicio<Cine, Long> {

	
	private final ICine cineRepository;

	public CineService(ICine cineRepository) {
		this.cineRepository = cineRepository;
	}
	
	public Cine validateInput(@Valid Cine cine) throws ConstraintViolationException, DomainException {
		if (cine == null) {
			throw new DomainException("El registro no es valido");
		}
		return cine;
	}
	
	@Override
	public Cine insert(Cine c) throws DAOException, ConstraintViolationException, DomainException {
		Cine cine = validateInput(c);
		List<Long> list_entradas = cine.getCi_lista_entradas();
		if (Rutinas.isEmptyOrNull(list_entradas)) {		
			list_entradas = new ArrayList<Long>();
		}
		
		cine.setCi_lista_entradas(list_entradas);
		if (cine.isValidInsert()) {
			return cineRepository.save(cine);
		} else {
			throw new DAOException("El registro no se puede insertar");
		}
	}

	@Override
	public List<Cine> listAll() {
		return cineRepository.findAll();
	}

	@Override
	public boolean update(Cine cine) throws DomainException, DAOException {

		Optional<Cine> cineDBO = cineRepository.findById(cine.getId_cine());
		if (cineDBO.isEmpty()) {
			throw new DAOException("El registro ya no existe");
		}
		Cine cineDB = cineDBO.get();
		if (Objects.nonNull(cine.getCi_nombre()) && !"".equalsIgnoreCase(cine.getCi_nombre())) {
			cineDB.setCi_nombre(cine.getCi_nombre());
		}
		if (Objects.nonNull(cine.getCi_calle()) && !"".equalsIgnoreCase(cine.getCi_calle())) {
			cineDB.setCi_calle(cine.getCi_calle());
		}
		if (Objects.nonNull(cine.getCi_barrio()) && !"".equalsIgnoreCase(cine.getCi_barrio())) {
			cineDB.setCi_barrio(cine.getCi_barrio());
		}

		if (Objects.nonNull(cine.getCi_capacidad())) {
			cineDB.setCi_capacidad(cine.getCi_capacidad());
		}
		if (cine.isValidUpdate()) {
			return cineRepository.save(cineDB) != null;
		} else {
			throw new DAOException("El registro no es valido para actualizacion");
		}
	}

	@Override
	public boolean deleteById(Long id_cine) {
		cineRepository.deleteById(id_cine);
		return true;

	}

	@Override
	public Optional<Cine> leerUno(Long id) {
		return cineRepository.findById(id);

	}
	
	public List<CineProjectionNombre> getAllCineProjectionNombre(){
		return cineRepository.findAllCineProjectionNombre();
	}

	public boolean addEntrada(Entrada entrada) throws DomainException, DAOException {

		Optional<Cine> cineDBO = cineRepository.findById(entrada.getEntCine());
		if (cineDBO.isEmpty()) {
			throw new DAOException("El registro ya no existe");
		}
		Cine cineDB = cineDBO.get();
		List<Long> list_entradas = cineDB.getCi_lista_entradas();
		if (Rutinas.isEmptyOrNull(list_entradas)) {		
			list_entradas = new ArrayList<Long>();
		}
		list_entradas.add(entrada.getId_entrada());
		cineDB.setCi_lista_entradas(list_entradas);

		return cineRepository.save(cineDB) != null;
	}

	@Override
	public boolean existsById(Long s) {
		return cineRepository.existsById(s);
	}

}
