package com.recursosformacion.lcs.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.interfaces.Modelo;
import com.recursosformacion.lcs.util.Constantes;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;


public abstract class IServicioMas<T extends Modelo<S >, 
                                   S extends Long, 
                                   U extends JpaRepository<T, S >> 
                         implements IServicio<T, S >{
	
	U cDao;
	
	protected IServicioMas(final U repo){
		this.cDao = repo;
	}
	
	public T insert(@Valid final T t) throws DomainException, ConstraintViolationException, DAOException {
		t.setId((S) Long.valueOf(0));
		if (t.isValidInsert()) {
			return cDao.save(t);
		} else {
			throw new DAOException(Constantes.MSJ_ERROR_INSERT_VALID);
		}
    }
	
	public T update(@Valid final T t) throws DomainException, DAOException {
		if (!existe(t.getId())) {
			throw new DAOException(String.format(Constantes.MSJ_ERROR_REGISTRO_N, t.getId() ));
		}
		return cDao.save(t);
	}
	
	public abstract T patch(final T t) throws DomainException, DAOException ;

	public boolean borrar(final T t) throws DAOException {
		if (!existe(t.getId())) {
			throw new DAOException(String.format(Constantes.MSJ_ERROR_REGISTRO_N, t.getId() ));
		}
        cDao.delete(t);
        return true;
    }
		
	public boolean borrarPorId(final S s) throws DAOException {
        if (!existe(s)) {
        	throw new DAOException(String.format(Constantes.MSJ_ERROR_REGISTRO_N,s ));
        }
        cDao.deleteById(s);
        return true;
    }
		
	public  List<T> listarTodos() {
		return cDao.findAll();
    }

	public Optional<T> leerUno(final S s) {		
		return cDao.findById(s);
	}

	public boolean existe(final S s) {
		return cDao.existsById(s);
	}

}
