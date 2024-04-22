package com.recursosformacion.lcs.controller;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.exception.ControllerException;
import com.recursosformacion.lcs.model.dto.CineDTO;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.service.CineService;

import jakarta.validation.ConstraintViolationException;

import com.recursosformacion.lcs.exception.ControllerException;


@ExtendWith(MockitoExtension.class)
class CineControllerTestJava {
		
	@Mock
	private CineService cDao;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	CineController cineController;
	
	CineDTO cineDTO;
	
	Cine cine;
	Cine cineExistente;
	Cine cineErrorNombre;
	Cine cineErrorCalle;
	Cine cineErrorCapacidad;
	String cineJson;
	String cineExistenteJson;
	String cineErrorNombreJson;
	String cineErrorCalleJson;
	String cineErrorCapacidadJson;
	
	Optional<Cine> cineOptional;
	CineProjectionNombre cineProjectionNombre;
	
	@BeforeEach
	void setup() throws JsonProcessingException {
		this.cine = new Cine(1L, "Cine1", "Calle 1","Barrio 1", 300,null);
		this.cineDTO = new CineDTO(1L, "Cine1", "Calle 1","Barrio 1", 300,null);
		this.cineJson = this.objectMapper.writeValueAsString(cine);
		this.cineExistente = new Cine(10L, "Cine10", "Calle 10","Barrio 10", 500,null);
		this.cineExistenteJson = this.objectMapper.writeValueAsString(cineExistente);
		this.cineErrorNombre = new Cine(0L, "", "Calle 1","Barrio 1", 300, null);	
		this.cineErrorCalle = new Cine(0L, "Cine1", "","Barrio 1", 300, null);
		this.cineErrorCapacidad = new Cine(0L, "Cine1", "Calle 1","Barrio 1", 0, null);
		this.cineErrorNombreJson = this.objectMapper.writeValueAsString(cineErrorNombre);
		this.cineErrorCalleJson = this.objectMapper.writeValueAsString(cineErrorCalle);
		this.cineErrorCapacidadJson = this.objectMapper.writeValueAsString(cineErrorCapacidad);
		
		this.cineOptional = Optional.of(cine);
		this.cineProjectionNombre = new CineProjectionNombre(1L, "Cine1", "Barrio 1");

        cineController = new CineController(cDao);
	}
	
	@Test
	void whenGetAll_thenReturns200() throws Exception {
		List<Cine> lcine = Arrays.asList(this.cine);
		when(cDao.listAll())
			.thenReturn(lcine);
		ResponseEntity<Map<String, Object>> response = cineController.leerTodos();

		// Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertTrue(compararListas(Arrays.asList(this.cineDTO), (List<CineDTO>)response.getBody().get("data")));  
	}
//
////	// Comprobando que esta activo por direccion
//	@Test
//	void whenGetAll_direction_thenReturns200() throws Exception {
//		List<CineProjectionNombre> lcine = Arrays.asList(this.cineProjectionNombre);
//		when(cDao.getAllCineProjectionNombre()).thenReturn(lcine);
//		ResponseEntity<Map<String, Object>> response = cineController.leerDirecciones();
//		
//		// Verificar
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().get("status"));
//        assertTrue(compararListas(Arrays.asList(this.cineDTO), (List<CineDTO>)response.getBody().get("data")));  
//    	
//	}
//	
//	
//	
	@Test
	void whenGetOneExist_thenReturns200() throws Exception {
		when(cDao.leerUno(1L)).thenReturn(cineOptional);
		ResponseEntity<Map<String, Object>> response = cineController.leerUno(1L);
		
		// Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertTrue(compararCines(cineDTO, (CineDTO)response.getBody().get("data")));
	}
//	
	@Test
	void whenGetOneNotExist_thenReturns200() throws Exception {
		when(cDao.leerUno(1000L)).thenReturn(Optional.empty());
		assertThrows(ControllerException.class, () -> cineController.leerUno(1000L));
	}
//	
	@Test
	void whenPostOk_thenReturns200() throws Exception {
		when(cDao.insert(any(Cine.class))).thenReturn(this.cine);
		ResponseEntity<Map<String, Object>> response = cineController.alta(cineDTO);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals("Registro salvado", response.getBody().get("message"));
	}
//	
	@Test
	void whenPostErrorNombre_thenReturns400() throws Exception {
		when(cDao.insert(any(Cine.class))).thenThrow(ConstraintViolationException.class);
		assertThrows(ControllerException.class, () -> cineController.alta(cineController.convertToDto(cineErrorNombre)));
	}

	@Test
	void whenPutOk_thenReturns200() throws Exception {
		when(cDao.update(any(Cine.class))).thenReturn(true);
		ResponseEntity<Map<String, Object>> response = cineController.modificacion(cineDTO);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals("Actualizacion correcta", response.getBody().get("message"));

	}
//	
	@Test
	void whenPutError_thenReturns400() throws Exception {
		when(cDao.update(any(Cine.class))).thenReturn(false);
		assertThrows(ControllerException.class, () -> cineController.modificacion(cineDTO));
	}
	
	@Test
	void whenDeleteOk_thenReturns200() throws Exception {
		when(cDao.deleteById(1L)).thenReturn(true);
		ResponseEntity<Map<String, Object>> response = cineController.eliminar(1L);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals("Registro borrado", response.getBody().get("message"));
	}
	
	@Test
	void whenDeleteError_thenReturns400() throws Exception {
		when(cDao.deleteById(1L)).thenThrow(ConstraintViolationException.class);
		assertThrows(ControllerException.class, () -> cineController.eliminar(1L));
	
		
	}
	
	
	public String convertirAJson(CineDTO cine) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(cine);
	}
	
	/**
	 * Compara dos objetos CineDTO devolviendo true/false
	 * @return
	 */
	public boolean compararCines(CineDTO cine, CineDTO other) {
	    if (cine == null && other == null) {
	        return true;
	    } else if (cine == null || other == null) {
	        return false;
	    }

	    return cine.getCi_barrio().compareTo(other.getCi_barrio())==0 	    
	    		& cine.getCi_calle().compareTo(other.getCi_calle())==0
				& cine.getCi_capacidad() == other.getCi_capacidad()
				& cine.getCi_nombre().compareTo(other.getCi_nombre())==0 
				& cine.getId_cine()==other.getId_cine();
	}
	
	/**
	 * Compara dos listas de CineDTO, comprobando que tienen los mismos elementos, 
	 * aunque no obliga a que esten en el mismo orden
	 */
	public boolean compararListas(List<CineDTO> lista1,List<CineDTO> lista2) {
		List<CineDTO> lista = lista1.stream()
				.filter(f-> !listaContain(f,lista2))
				.collect(Collectors.toList());
		return lista.size()==0 && lista1.size()==lista2.size();
	}
	
	public boolean listaContain(CineDTO obj, List<CineDTO> lista1) {
		for (CineDTO element : lista1) { 
	        if (!compararCines(element, obj)) { 
	        	System.out.println("element -" + element);
	    		System.out.println("obj-" + obj);
	            return false; 
	        } 
		}
		return true;	
	}
}
