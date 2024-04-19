package com.recursosformacion.lcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;

@org.springframework.stereotype.Repository
public interface ICine extends JpaRepository<Cine, Long>{

	  @Query("SELECT new com.recursosformacion.lcs.model.dto.CineProjectionNombre(c.id_cine, c.ci_nombre, c.ci_barrio) FROM Cine c")
	    List<CineProjectionNombre> findAllCineProjectionNombre();
}
