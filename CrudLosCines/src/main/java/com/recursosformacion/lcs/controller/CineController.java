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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.recursosformacion.lcs.exception.ControllerException;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.model.dto.CineDTO;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.service.CineService;
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
			map.put("status", 1);
			map.put("data", cat);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No existen datos");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@CheckCineValidation() @PathVariable("id") Long id)
			throws ConstraintViolationException, ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Optional<Cine> cineDB = cDao.leerUno(id);
		if (cineDB.isPresent()) {
            // Convertir a DTO
            CineDTO cineDTO = convertToDto(cineDB.get());
            // Retornar el DTO
            map.put("status", 1);
            map.put("data", cineDTO);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new ControllerException("No se encontro el registro");
        }
		
	}

	@GetMapping({ "", "/" })
	public ResponseEntity<Map<String, Object>> leerTodos() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Cine> cat = cDao.listAll();
		if (!cat.isEmpty()) {
			List<CineDTO> cDTO = cat.stream()
					.map(cine -> convertToDto(cine))
					.collect(Collectors.toList());		
			map.put("status", 1);
			map.put("data", cDTO);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No existen datos");
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody CineDTO c)
			throws DomainException, ControllerException, DAOException { // ID,NOMBRE,DESCRIPCION
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		c.setId_cine(0);
		try {
			Cine cine = cDao.insert(convertToEntity(c));
			map.put("status", 1);
			map.put("message", "Registro salvado");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ControllerException("Error al hacer la insercion " + ex.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modificacion(@Valid @RequestBody CineDTO c)
			throws ControllerException, DomainException, DAOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if (cDao.update(convertToEntity(c))==true) {
			map.put("status", 1);
			map.put("message", "Actualizacion correcta");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("Error al hacer la modificacion " + c.toString() );

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@CheckCineValidation() @NotNull @PathVariable("id") Long id)
			throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			cDao.deleteById(id);
			map.put("status", 1);
			map.put("message", "Registro borrado");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ControllerException("Error al borrar");
		}
	}

	@RequestMapping("/test")
	public @ResponseBody String greeting() {
		return "Hello, World";
	}
	
	public CineDTO convertToDto(Cine cine) {
		CineDTO cineDTO = mapper.map(cine, CineDTO.class);
		return cineDTO;
	}
	
	public Cine convertToEntity(CineDTO cineDTO) {
		Cine cine = mapper.map(cineDTO, Cine.class);
		return cine;
	}
}
