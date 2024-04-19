package com.recursosformacion.lcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.recursosformacion.lcs.persistence.entity.Programa;



public interface IPrograma extends JpaRepository<Programa, Long>{

}
