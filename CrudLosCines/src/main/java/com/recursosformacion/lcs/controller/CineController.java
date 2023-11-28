package com.recursosformacion.lcs.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
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
import com.recursosformacion.lcs.model.Cine;
import com.recursosformacion.lcs.service.CineService;

import jakarta.validation.Valid;


@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/cine")
public class CineController {
	
	@Autowired
	private CineService cDao;
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@PathVariable("id") String ids) throws ControllerException {
		String mensaje ="";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (ids != null) {
			try {
				Long id = Long.parseLong(ids);
				Optional<Cine> cineDB = cDao.leerUno(id);

				if (cineDB.isPresent()) {
					map.put("status", 1);
					map.put("data", cineDB.get());
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					mensaje =  "No existen datos";
				}
			} catch (NumberFormatException nfe) {
				mensaje = "Formato erroneo";
			}
		} else {
			mensaje="Formato erroneo";
		}
		throw new ControllerException(mensaje);

	}

	
	@GetMapping({"","/"})
	public ResponseEntity<Map<String, Object>> leerTodos() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Cine> cat = cDao.listAll();
		if (!cat.isEmpty()) {
			System.out.println(cat);
			map.put("status", 1);
			map.put("data", cat);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No existen datos");
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody Cine c, BindingResult bindingResult) throws DomainException, ControllerException, DAOException {		//ID,NOMBRE,DESCRIPCION
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (!bindingResult.hasErrors()) {
			c.setId_cine(0);
			c=cDao.insert(c);
			if (c!=null) {
				System.out.println("En alta-" + c.toString());
				map.put("status", 1);
				map.put("message", "Registro salvado");
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
				throw new ControllerException("Error al hacer la insercion");
			}
		}
		throw new ControllerException("Error de datos al hacer la insercion");
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modificacion(@RequestBody Cine c) throws ControllerException, DomainException, DAOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (cDao.update(c)) {
			map.put("status", 1);
			map.put("message", "Actualizacion correcta");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("Error al hacer la modificacion");

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable("id") String ids) throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (ids != null) {
			try {
				long id = Long.parseLong(ids);
				Optional<Cine> cineDB = cDao.leerUno(id);
				cDao.deleteById(cineDB.get().getId_cine());
				map.put("status", 1);
				map.put("message", "Registro borrado");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			} catch (Exception ex) {
				throw new ControllerException("Error al borrar");

			}
		}
		throw new ControllerException("No existe registro al borrar");
	}

}
