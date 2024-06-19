package com.recursosformacion.lcs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Pelicula;
import com.recursosformacion.lcs.repository.IPelicula;
import com.recursosformacion.lcs.service.interfaces.IServicioMas;
import com.recursosformacion.lcs.util.Constantes;

@Service
public class PeliculaService extends IServicioMas<Pelicula, Long, IPelicula>{

	private final IPelicula cDao;
	
	PeliculaService(IPelicula peliculaRepository){
		super(peliculaRepository);
		this.cDao = peliculaRepository;
	}
	

	
	@Override
	public Pelicula patch(Pelicula peli) throws DomainException, DAOException {
		Optional<Pelicula> dbo = cDao.findById(peli.getId_pelicula());
		
		if (dbo.isEmpty()) {
			throw new DAOException(String.format(Constantes.MSJ_ERROR_REGISTRO_N, peli.getId() ));
		}
		Pelicula peliDbo = dbo.get();
		if (peli.getPe_titulo() != null) {
			peliDbo.setPe_titulo(peli.getPe_titulo());
		}
		if (peli.getPe_identificador() != 0) {
			peliDbo.setPe_identificador(peli.getPe_identificador());
		}
		return cDao.save(peliDbo);
	}
	
	

	
}
