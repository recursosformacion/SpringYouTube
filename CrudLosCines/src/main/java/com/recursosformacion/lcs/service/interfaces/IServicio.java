package com.recursosformacion.lcs.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;


@Service
public interface  IServicio<T, S> {

	public T insert(@Valid final T t) throws DomainException,ConstraintViolationException, DAOException ;
	public T update(@Valid final T t) throws DomainException,ConstraintViolationException, DAOException ;
	public T patch(final T t) throws DomainException, DAOException ;
	public boolean borrar(final T t) throws DAOException;
	public boolean borrarPorId(S s) throws DAOException;
	public List<T> listarTodos();
	public Optional<T>leerUno(S s);
	public boolean existe(S s);
}
