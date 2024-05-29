package com.recursosformacion.lcs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Pelicula;
import com.recursosformacion.lcs.repository.IPelicula;
import com.recursosformacion.lcs.service.interfaces.IServicioMas;

@Service
public class PeliculaService extends IServicioMas<Pelicula, Long, IPelicula>{

	private final IPelicula cDao;
	
	PeliculaService(IPelicula peliculaRepository){
		super(peliculaRepository);
		this.cDao = peliculaRepository;
	}
	
	@Override
	public Pelicula insert(Pelicula peli) throws DomainException, DAOException {
		peli.setId_pelicula(0);
		return cDao.save(peli);
	}
	
	@Override
	public Pelicula update(Pelicula peli) throws DomainException, DAOException {
		Optional<Pelicula> dbo = cDao.findById(peli.getId_pelicula());
		if (dbo.isEmpty()) {
			throw new DAOException("El registro:" + peli.getId_pelicula() + ", ya no existe");
		}
		return cDao.save(peli);
	}
	
	@Override
	public Pelicula patch(Pelicula peli) throws DomainException, DAOException {
		Optional<Pelicula> dbo = cDao.findById(peli.getId_pelicula());
		
		if (dbo.isEmpty()) {
			throw new DAOException("El registro:" + peli.getId_pelicula() + ", ya no existe");
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
	
	@Override
	public boolean borrar(Pelicula peli) throws DAOException {
        Optional<Pelicula> dbo = cDao.findById(peli.getId_pelicula());
        if (dbo.isEmpty()) {
            throw new DAOException("El registro:" + peli.getId_pelicula() + ", ya no existe");
        }
        cDao.delete(peli);
        return true;
    }

	
}
