package com.recursosformacion.lcs.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.model.dto.EntradaDTO;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.service.EntradaService;
import com.recursosformacion.lcs.util.Constantes;
import com.recursosformacion.lcs.util.constraint.interfaces.CheckEntradaValidation;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/entrada")
public class EntradaController {

	private final EntradaService cDao;

	private final CineService cDaoCine;

	EntradaController(EntradaService cDao, CineService cDaoCine) {
		this.cDao = cDao;
		this.cDaoCine = cDaoCine;

	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@CheckEntradaValidation @PathVariable("id") Long id)
			throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Optional<Entrada> entradaDB = cDao.leerUno(id);

		if (entradaDB.isPresent()) {
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, entradaDB.get());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException(Constantes.MSJ_NO_EXISTEN_DATOS);			
		}

	}

	@GetMapping({ "", "/" })
	public ResponseEntity<Map<String, Object>> leerTodos() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Entrada> cat = cDao.listAll();

		if (!cat.isEmpty()) {
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, cat);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException(Constantes.MSJ_NO_EXISTEN_DATOS);

		}
	}

	@GetMapping("/leerporid/{idCliente}")
	public ResponseEntity<Map<String, Object>> leerPorId(@PathVariable("idCliente") String id)
			throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Entrada> entradas = cDao.findByIdCliente(id);
		if (!entradas.isEmpty()) {
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, entradas);
			return new ResponseEntity<>(map, HttpStatus.OK);

		} else {
			throw new ControllerException(Constantes.MSJ_NO_EXISTEN_DATOS);

		}
	}

	@GetMapping("/leerporcine/{idCine}")
	public ResponseEntity<Map<String, Object>> leerporcine(@PathVariable("idCine") Long id) throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Entrada> entradas = cDao.findByEntCine(id);
		if (!entradas.isEmpty()) {
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, entradas);
			return new ResponseEntity<>(map, HttpStatus.OK);

		} else {
			throw new ControllerException(Constantes.MSJ_ERROR_CINE_N + id);

		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody EntradaDTO c)
			throws DomainException, ControllerException, DAOException { // ID,NOMBRE,DESCRIPCION
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Entrada e = convertirDTO(c);
		e.setId_entrada(0l);

		e = cDao.insert(e);
		if (e != null) {
			cDaoCine.addEntrada(e);
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.DATOS, e);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException(Constantes.MSJ_ERROR_INSERT);
		}
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modificacion(@Valid @RequestBody EntradaDTO c)
			throws ControllerException, DomainException, DAOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Entrada e = convertirDTO(c);
		if (cDao.update(e)) {
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.MENSAJE, Constantes.MSJ_ACTUALIZACION_OK);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException(Constantes.MSJ_ERROR_UPDATE);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@CheckEntradaValidation @PathVariable("id") Long id)
			throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			cDao.deleteById(id);
			map.put(Constantes.STATUS, 1);
			map.put(Constantes.MENSAJE, Constantes.MSJ_ELIMINACION_OK);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ControllerException(Constantes.MSJ_ERROR_DELETE);
		}

	}

	public Entrada convertirDTO(EntradaDTO entradaDTO)  {

		Entrada entrada = new Entrada();
		if (Objects.isNull(entradaDTO.getId_entrada())) {
			entradaDTO.setId_entrada(0L);
		}
		entrada.setId_entrada(entradaDTO.getId_entrada());
		entrada.setEnt_fila(entradaDTO.getEnt_fila());
		entrada.setEnt_numero(entradaDTO.getEnt_numero());
		entrada.setEnt_fecha_str(entradaDTO.getEnt_fecha_str());
		entrada.setIdCliente(entradaDTO.getIdCliente());
		entrada.setEntCine(entradaDTO.getEntCine());

		return entrada;
	}

}
