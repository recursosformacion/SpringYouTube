package com.recursosformacion.lcs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.repository.IEntrada;
import com.recursosformacion.lcs.service.interfaces.IServicio;
import com.recursosformacion.lcs.service.interfaces.IServicioMas;
import com.recursosformacion.lcs.util.Rutinas;

@Service
public class EntradaService extends IServicioMas<Entrada, Long, IEntrada> {

	
	private final IEntrada entradaRepository;
	
	EntradaService(IEntrada entradaRepository){
		super(entradaRepository);
		this.entradaRepository = entradaRepository;
	}
	
	public List<Entrada> entradaPorIdCliente(String id){
		return entradaRepository.findByIdCliente(id);
	}

	@Override
	public Entrada update(Entrada entrada) throws DomainException,DAOException {

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

		return entradaRepository.save(entradaDB);
	}
	
	public List<Entrada> buscarPorEntCine(Long id){
		return entradaRepository.findByEntCine(id);
	}
	
	public List<Entrada> buscarPorIdCliente(String id) {
		return entradaRepository.findByIdCliente(id);
	}

	@Override
	public boolean existe(Long s) {

		return entradaRepository.existsById(s);
	}

	@Override
	public Entrada patch(Entrada t) throws DomainException, DAOException {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

}
