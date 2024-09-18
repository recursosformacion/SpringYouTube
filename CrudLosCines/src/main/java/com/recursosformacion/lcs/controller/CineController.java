package com.recursosformacion.lcs.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.modelmapper.ModelMapper;
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
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.model.dto.CineDTO;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.util.Constantes;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckCineValidation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping("/api/cine")
@Validated
public class CineController {

	private final CineService cDao;

	private ModelMapper mapper = new ModelMapper();

	CineController(CineService cDao) {
		this.cDao = cDao;
	}

	@GetMapping("/direccion")
	public ResponseEntity<Map<String, Object>> leerDirecciones() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<CineProjectionNombre> cat = cDao.getAllCineProjectionNombre();
		if (!cat.isEmpty()) {
			System.out.println(cat);
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, cat);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException(Constantes.MSJ_NO_EXISTEN_DATOS);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@CheckCineValidation() @PathVariable("id") Long id)
			throws ConstraintViolationException, ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Optional<Cine> cineDB = cDao.leerUno(id);
		// Convertir a DTO
		CineDTO cineDTO = convertToDto(cineDB.get());
		// Retornar el DTO
		map.put(Constantes.STATUS, 1);
		map.put(Constantes.DATOS, cineDTO);
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@GetMapping({"/",""})
	public ResponseEntity<Map<String, Object>> leerTodos() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Cine> listaCines = cDao.listarTodos();
		if (!listaCines.isEmpty()) {
			List<CineDTO> cDTO = listaCines.stream()
					.map(cine -> convertToDto(cine))
					.collect(Collectors.toList());
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, cDTO);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException(Constantes.MSJ_NO_EXISTEN_DATOS);
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody CineDTO c)
			throws DomainException, ControllerException, DAOException { 
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			Cine cineIns = cDao.insert(convertToEntity(c));
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, convertToDto(cineIns));
			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception ex) {
			throw new ControllerException(Constantes.MSJ_ERROR_INSERT + ex.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modificacion(@Valid @RequestBody CineDTO c)
			throws ControllerException, DomainException, DAOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		try {
			Cine cineDb = cDao.update(convertToEntity(c));
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, convertToDto(cineDb));
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ControllerException(Constantes.MSJ_ERROR_UPDATE + ex.getMessage());

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@CheckCineValidation() @NotNull @PathVariable("id") Long id)
			throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			cDao.borrarPorId(id);
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.MENSAJE, Constantes.MSJ_ELIMINACION_OK);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ControllerException(Constantes.MSJ_ERROR_DELETE + ex.getMessage());
		}
	}

	@GetMapping("/test")
	public String greeting() {
		return "Hello, World";
	}

	public CineDTO convertToDto(Cine cine) {
		
		return mapper.map(cine, CineDTO.class);
	}

	public Cine convertToEntity(CineDTO cineDTO) {
		System.out.println(cineDTO.toString());
		if (cineDTO.getId() == null) {
			cineDTO.setId_cine(0);
		}
		cineDTO.setId_cine(0);
		return mapper.map(cineDTO, Cine.class);
	}
}
