package com.recursosformacion.lcs.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recursosformacion.lcs.persistence.entity.Entrada;



public interface IEntrada extends JpaRepository<Entrada, Long>{

	List<Entrada> findByIdCliente(String idCliente);
	List<Entrada> findByEntCine(Long entCine);
}
