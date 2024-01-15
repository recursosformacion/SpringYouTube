package com.recursosformacion.lcs.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;


@Service
public interface  IServicio<T, S> {

	public T insert(T t) throws DomainException, DAOException ;
	public boolean update(T t) throws DomainException, DAOException ;
	public boolean deleteById(S s) throws DAOException;
	public List<T> listAll();
	public Optional<T>leerUno(S s);
}
