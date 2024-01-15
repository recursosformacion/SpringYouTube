package com.recursosformacion.lcs.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.model.Entrada;
import com.recursosformacion.lcs.repository.IEntrada;
import com.recursosformacion.lcs.service.interfaces.IServicio;
import com.recursosformacion.lcs.util.Rutinas;

@Service
public class EntradaService implements IServicio<Entrada, Long> {

	
	private final IEntrada entradaRepository;
	
	EntradaService(IEntrada entradaRepository){
		this.entradaRepository = entradaRepository;
	}

	@Override
	public Entrada insert(Entrada entrada) {
		return entradaRepository.save(entrada);
	}
	@Override
	public List<Entrada> listAll() {
		return  entradaRepository.findAll();
	}
	
	public List<Entrada> entradaPorIdCliente(String id){
		return entradaRepository.findByIdCliente(id);
	}

	@Override
	public boolean update(Entrada entrada) throws DomainException,DAOException {

		Optional<Entrada> entradaDBO = entradaRepository.findById(entrada.getId_entrada());
		if (entradaDBO.isEmpty()) {
			throw new DAOException("El registro:" + entrada.getId_entrada() + ", ya no existe");
		}
		Entrada entradaDB = entradaDBO.get();

		entradaDB.setIdCliente(Rutinas.nuevoSiNoVacio(entradaDB.getIdCliente(), entrada.getIdCliente()));
		entradaDB.setEnt_fecha(Rutinas.nuevoSiNoVacio(entradaDB.getEnt_fecha(), entrada.getEnt_fecha()));
		entradaDB.setEnt_fila(Rutinas.nuevoSiNoVacio(entradaDB.getEnt_fila(), entrada.getEnt_fila()));
		entradaDB.setEnt_numero(Rutinas.nuevoSiNoVacio(entradaDB.getEnt_numero(), entrada.getEnt_numero()));
		entradaDB.setEnt_cine(Rutinas.nuevoSiNoVacio(entradaDB.getEnt_cine(), entrada.getEnt_cine()));

		return entradaRepository.save(entradaDB) != null;
	}

	@Override
	public boolean deleteById(Long id_entrada) throws DAOException {
		Optional<Entrada> entradaDBO = entradaRepository.findById(id_entrada);
		if (entradaDBO.isEmpty()) {
			throw new DAOException("El registro:" + id_entrada + ", ya no existe");
		}
		 entradaRepository.deleteById(id_entrada);
		 return true;

	}

	@Override
	public Optional<Entrada> leerUno(Long id) {
		return entradaRepository.findById(id);		
	}
	
	public List<Entrada> findByIdCliente(String id){
		return entradaRepository.findByIdCliente(id);
	}

}
