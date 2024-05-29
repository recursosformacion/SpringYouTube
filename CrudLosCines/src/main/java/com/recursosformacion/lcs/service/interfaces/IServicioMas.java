package com.recursosformacion.lcs.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import org.springframework.data.jpa.repository.JpaRepository;


public abstract class IServicioMas<T, S, U extends JpaRepository<T, S>> implements IServicioNew<T, S>{
	
	U cDao;
	
	protected IServicioMas(final U peliculaRepository){
		this.cDao = peliculaRepository;
	}
	
	public T insert(final T t) throws DomainException, DAOException {
		return cDao.save(t);
    }
	
	public T update(final T t) throws DomainException, DAOException {
		return cDao.save(t);
	}
	
	public abstract T patch(final T t) throws DomainException, DAOException ;

	public boolean borrar(final T t) throws DAOException {
        cDao.delete(t);
        return true;
    }
		
	public boolean borrarPorId(final S s) throws DAOException {
        if (!existe(s)) {
            throw new DAOException("El registro:" + s + ", ya no existe");
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
