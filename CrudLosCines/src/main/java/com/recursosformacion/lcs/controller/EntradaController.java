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
import com.recursosformacion.lcs.model.Entrada;
import com.recursosformacion.lcs.model_dto.EntradaDTO;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.service.EntradaService;

import jakarta.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api/entrada")
public class EntradaController {

	private final EntradaService cDao;

	private final CineService cDaoCine;

	EntradaController(EntradaService cDao, CineService cDaoCine){
		this.cDao = cDao;
		this.cDaoCine = cDaoCine;
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> leerUno(@PathVariable("id") String ids) throws ControllerException {
		String mensaje = "";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (ids != null) {
			try {
				Long id = Long.parseLong(ids);
				Optional<Entrada> entradaDB = (Optional<Entrada>) cDao.leerUno(id);

				if (entradaDB.isPresent()) {
					map.put("status", 1);
					map.put("data", entradaDB.get());
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					mensaje = "No existen datos";
				}
			} catch (NumberFormatException nfe) {
				mensaje = "Formato erroneo";
			}
		} else {
			mensaje = "Formato erroneo";
		}
		throw new ControllerException(mensaje);

	}

	@GetMapping({ "", "/" })
	public ResponseEntity<Map<String, Object>> leerTodos() throws ControllerException {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Entrada> cat = cDao.listAll();

		if (!cat.isEmpty()) {
			map.put("status", 1);
			map.put("data", cat);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("No existen datos");

		}
	}

	@GetMapping("/leerporid/{idCliente}")
	public ResponseEntity<Map<String, Object>> leerPorId(@PathVariable("idCliente") String id) throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Entrada> entradas = cDao.findByIdCliente(id);
		if (!entradas.isEmpty()) {
			map.put("status", 1);
			map.put("data", entradas);
			return new ResponseEntity<>(map, HttpStatus.OK);
			
		} else {
			throw new ControllerException("No existen datos");

		}
	}
	@GetMapping("/leerporcine/{idCine}")
	public ResponseEntity<Map<String, Object>> leerporcine(@PathVariable("idCine") Long id) throws ControllerException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Entrada> entradas = cDao.findByEntCine(id);
		if (!entradas.isEmpty()) {
			map.put("status", 1);
			map.put("data", entradas);
			return new ResponseEntity<>(map, HttpStatus.OK);
			
		} else {
			throw new ControllerException("No existen datos para cine " + id);

		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> alta(@Valid @RequestBody EntradaDTO c  ) 
									throws DomainException, ControllerException, DAOException { // ID,NOMBRE,DESCRIPCION
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Entrada e = convertirDTO(c);
		e.setId_entrada(0l);

		e = cDao.insert(e);
		if (e != null) {
			cDaoCine.addEntrada(e);
			map.put("status", 1);
			map.put("data", e);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new ControllerException("Error al hacer la insercion");
		}
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modificacion(@Valid @RequestBody EntradaDTO c)
			throws ControllerException, DomainException, DAOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Entrada e = convertirDTO(c);
		if (cDao.update(e)) {
			map.put("status", 1);
			map.put("message", "Actualizacion realizada");
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
				Optional<Entrada> entradaDB = cDao.leerUno(id);
				cDao.deleteById(entradaDB.get().getId_entrada());
				map.put("status", 1);
				map.put("message", "Registro borrado");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			} catch (Exception ex) {
				throw new ControllerException("Error al borrar");

			}
		}
		throw new ControllerException("No existe registro al borrar");
	}

	public Entrada convertirDTO(EntradaDTO d) throws ControllerException {

		Entrada e = new Entrada();
		if (Objects.isNull(d.getId_entrada())) {
			d.setId_entrada(0L);
		}
		e.setId_entrada(d.getId_entrada());
		e.setEnt_fila(d.getEnt_fila());
		e.setEnt_numero(d.getEnt_numero());
		e.setEnt_fecha_str(d.getEnt_fecha());
		e.setIdCliente(d.getIdCliente());
		e.setEntCine(d.getEntCine());
		
		return e;
	}

}
