package com.recursosformacion.lcs.service;

import java.util.List;
import java.util.Optional;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Pelicula;
import com.recursosformacion.lcs.repository.IPelicula;
import com.recursosformacion.lcs.service.interfaces.IServicio;

public class PeliculaService implements IServicio<Pelicula, Long>{

	private final IPelicula peliculaRepository;
	
	PeliculaService(IPelicula peliculaRepository){
		this.peliculaRepository = peliculaRepository;
	}
	
	@Override
	public Pelicula insert(Pelicula t) throws DomainException, DAOException {
		
		return peliculaRepository.save(t);
	}

	@Override
	public boolean update(Pelicula t) throws DomainException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(Long s) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Pelicula> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Pelicula> leerUno(Long s) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long s) {
		// TODO Auto-generated method stub
		return false;
	}

	//create a method that returns a list of movies
	
}
