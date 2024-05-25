package com.recursosformacion.lcs.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recursosformacion.lcs.exception.ControllerException;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.model.dto.CineDTO;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.persistence.entity.Pelicula;
import com.recursosformacion.lcs.service.PeliculaService;
import com.recursosformacion.lcs.util.Constantes;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckCineValidation;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckEntradaValidation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/pelicula")
@Validated
public class PeliculaController {

	private final PeliculaService cDao;

	public PeliculaController(PeliculaService peliculaService) {
		this.cDao = peliculaService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@PathVariable("id") Long id)
			throws ConstraintViolationException, ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Optional<Pelicula> peli = cDao.leerUno(id);
		if (peli.isPresent()) {
			// Retornar el DTO
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, peli);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No se encontro el registro");
		}
	}
	
	@GetMapping({ "", "/" })
	public ResponseEntity<Map<String, Object>> leerTodos() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Pelicula> pelis = cDao.listarTodos();
		if (!pelis.isEmpty()) {	
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, pelis);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No existen datos");
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody Pelicula p)
			throws DomainException, ControllerException, DAOException { // ID,NOMBRE,DESCRIPCION
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		p.setId_pelicula(0);
		try {
			cDao.insert(p);
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, p);
			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception ex) {
			throw new ControllerException("Error al hacer la insercion " + ex.getMessage());
		}
	}
	
	@PutMapping("")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Pelicula p)
			throws ConstraintViolationException, ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
            cDao.update(p);
            map.put(Constantes.STATUS, 1);
            map.put(Constantes.DATOS, p);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ControllerException("Error al hacer la actualizacion " + ex.getMessage());
        }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> borrar( @PathVariable("id") Long id)
			throws ConstraintViolationException, ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			cDao.borrar(id);
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.MENSAJE, "Registro eliminado");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ControllerException("Error al hacer la eliminacion " + ex.getMessage());
		}
	}
}
