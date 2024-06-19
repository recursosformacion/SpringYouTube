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
import com.recursosformacion.lcs.persistence.entity.Pelicula;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.repository.ICine;
import com.recursosformacion.lcs.repository.IPelicula;
import com.recursosformacion.lcs.service.interfaces.IServicio;
import com.recursosformacion.lcs.service.interfaces.IServicioMas;
import com.recursosformacion.lcs.util.Constantes;
import com.recursosformacion.lcs.util.Rutinas;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;


@Service
@Validated
public class CineService extends IServicioMas<Cine, Long, ICine>{

	
	private final ICine cineRepository;

	public CineService(ICine cineRepository) {
		super(cineRepository);
		this.cineRepository = cineRepository;
	}
	
	@Override
	public Cine insert(@Valid Cine cine) throws DAOException, ConstraintViolationException, DomainException {

		List<Long> list_entradas = cine.getCi_lista_entradas();
		if (Rutinas.isEmptyOrNull(list_entradas)) {		
			list_entradas = new ArrayList<>();
		}
		
		cine.setCi_lista_entradas(list_entradas);
		if (cine.isValidInsert()) {
			return cineRepository.save(cine);
		} else {
			throw new DAOException(Constantes.MSJ_ERROR_INSERT_VALID);
		}
	}


	@Override
	public Cine update(Cine cine) throws DomainException, DAOException {

		Optional<Cine> cineDBO = leerUno(cine.getId_cine());
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
			return cineRepository.save(cineDB);
		} else {
			throw new DAOException("El registro no es valido para actualizacion");
		}
	}


	
	public List<CineProjectionNombre> getAllCineProjectionNombre(){
		return cineRepository.findAllCineProjectionNombre();
	}

	public boolean addEntrada(Entrada entrada) throws  DAOException {

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
	public Cine patch(Cine t) throws DomainException, DAOException {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

}
