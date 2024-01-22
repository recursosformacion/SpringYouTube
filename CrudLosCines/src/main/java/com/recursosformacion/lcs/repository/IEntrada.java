package com.recursosformacion.lcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recursosformacion.lcs.model.Entrada;



public interface IEntrada extends JpaRepository<Entrada, Long>{

	List<Entrada> findByIdCliente(String idCliente);
	List<Entrada> findByEntCine(Long entCine);
}
