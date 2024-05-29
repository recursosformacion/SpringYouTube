package com.recursosformacion.lcs.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;


@Service
public interface  IServicioNew<T, S> {

	public T insert(T t) throws DomainException, DAOException ;
	public T update(T t) throws DomainException, DAOException ;
	public T patch(T t) throws DomainException, DAOException ;
	public boolean borrar(T t) throws DAOException;
	public boolean borrarPorId(S s) throws DAOException;
	public List<T> listarTodos();
	public Optional<T>leerUno(S s);
	public boolean existe(S s);
}
