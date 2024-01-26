package com.recursosformacion.lcs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.persistence.repository.ICine;
import com.recursosformacion.lcs.service.IService;
import com.recursosformacion.lcs.util.Rutinas;
import org.springframework.stereotype.Service;

@Service
public class CineServiceImpl implements IService<Cine, Long> {

	@Autowired
	private ICine cineRepository;

	@Override
	public Cine insert(Cine cine) throws DAOException {
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

}
