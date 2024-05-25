package com.recursosformacion.lcs.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import org.springframework.data.jpa.repository.JpaRepository;


public abstract class IServicioMas<T, S, U extends JpaRepository<T, S>> {
	
	U cDao;
	
	protected IServicioMas(U peliculaRepository){
		this.cDao = peliculaRepository;
	}
	
	public T insert(T t) throws DomainException, DAOException {
		return cDao.save(t);
    }
	
	public T update(T t) throws DomainException, DAOException {
		return cDao.save(t);
	}
	
	public abstract T patch(T t) throws DomainException, DAOException ;
	
	public boolean borrar(S s) throws DAOException {
        if (!existe(s)) {
            throw new DAOException("El registro:" + s + ", ya no existe");
        }
        cDao.deleteById(s);
        return true;
    }
		
	public  List<T> listarTodos() {
		return cDao.findAll();
    }

	public Optional<T> leerUno(S s) {
		return cDao.findById(s);
	}

	public boolean existe(S s) {
		return cDao.existsById(s);
	}

}
