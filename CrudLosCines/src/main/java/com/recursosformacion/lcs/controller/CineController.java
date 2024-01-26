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
import com.recursosformacion.lcs.model_dto.CineProjectionNombre;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckCineValidation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/cine")
@Validated
public class CineController {

	private final CineService cDao;

	CineController(CineService cDao) {
		this.cDao = cDao;
	}

	@GetMapping("/direccion")
	public ResponseEntity<Map<String, Object>> leerDirecciones() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<CineProjectionNombre> cat = cDao.getAllCineProjectionNombre();
		if (!cat.isEmpty()) {
			System.out.println(cat);
			map.put("status", 1);
			map.put("data", cat);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No existen datos");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@PathVariable("id") @CheckCineValidation Long id) throws ConstraintViolationException{		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Optional<Cine> cineDB = cDao.leerUno(id);
		map.put("status", 1);
		map.put("data", cineDB.get());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping({ "", "/" })
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
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody Cine c)
			throws DomainException, ControllerException, DAOException { // ID,NOMBRE,DESCRIPCION

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		c.setId_cine(0);
		c = cDao.insert(c);
		if (c != null) {

			System.out.println("En alta-" + c.toString());
			map.put("status", 1);
			map.put("message", "Registro salvado");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("Error al hacer la insercion");
		}
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modificacion(@Valid @RequestBody Cine c)
			throws ControllerException, DomainException, DAOException {
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
