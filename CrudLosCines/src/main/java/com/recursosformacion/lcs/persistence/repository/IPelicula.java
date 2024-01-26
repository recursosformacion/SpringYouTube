package com.recursosformacion.lcs.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.recursosformacion.lcs.persistence.entity.Pelicula;



public interface IPelicula extends JpaRepository<Pelicula, Long>{

}
