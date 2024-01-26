package com.recursosformacion.lcs.service.impl;

import java.util.List;
import java.util.Optional;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.persistence.repository.IEntrada;
import com.recursosformacion.lcs.service.IService;
import com.recursosformacion.lcs.util.Rutinas;
import org.springframework.stereotype.Service;

@Service
public class EntradaServiceImpl implements IService<Entrada, Long> {

	
	private final IEntrada entradaRepository;
	
	EntradaServiceImpl(IEntrada entradaRepository){
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
		entradaDB.setEntCine(Rutinas.nuevoSiNoVacio(entradaDB.getEntCine(), entrada.getEntCine()));

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
	
	public List<Entrada> findByEntCine(Long id){
		return entradaRepository.findByEntCine(id);
	}

}
