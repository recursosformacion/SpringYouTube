package com.recursosformacion.lcs.controller;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.recursosformacion.lcs.model.dto.CineDTO;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.service.CineService;


@ExtendWith(MockitoExtension.class)
class CineControllerTestJava {
		
	@Mock
	private CineService cDao;
	
	@Autowired
	private CineController cineController;
	
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
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
		System.out.println(cine);
		this.cineJson = this.objectMapper.writeValueAsString(cine);
		this.cineExistente = new Cine(10L, "Cine10", "Calle 10","Barrio 10", 500,null);
		this.cineExistenteJson = this.objectMapper.writeValueAsString(cineExistente);
		this.cineErrorNombre = new Cine(1L, "", "Calle 1","Barrio 1", 300, null);	
		this.cineErrorCalle = new Cine(1L, "Cine1", "","Barrio 1", 300, null);
		this.cineErrorCapacidad = new Cine(1L, "Cine1", "Calle 1","Barrio 1", 0, null);
		this.cineErrorNombreJson = this.objectMapper.writeValueAsString(cineErrorNombre);
		this.cineErrorCalleJson = this.objectMapper.writeValueAsString(cineErrorCalle);
		this.cineErrorCapacidadJson = this.objectMapper.writeValueAsString(cineErrorCapacidad);
		
		this.cineOptional = Optional.of(cine);
		this.cineProjectionNombre = new CineProjectionNombre(1L, "Cine1", "Barrio 1");
		when(cDao.existsById(1L)).thenReturn(true);
		when(cDao.existsById(1L)).thenReturn(false);

        cineController = new CineController(cDao);

	}
	
//	@Test
//	void cargaControlador() throws Exception {
//		assertThat(controller).isNotNull();
//	}
	
//	@Test
//	void atiendeA_LaLlamadaDeTest() throws Exception {
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/cine/test",
//				String.class)).contains("Hello, World");
//	}
	
	@Test
	void whenGetAll_thenReturns200() throws Exception {
		List<Cine> lcine = Arrays.asList(this.cine);
		when(cDao.listAll())
			.thenReturn(lcine);
		ResponseEntity<Map<String, Object>> response = cineController.leerTodos();

		// Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals(lcine, response.getBody().get("data"));
   
	}

	// Comprobando que esta activo por direccion
	@Test
	void whenGetAll_direction_thenReturns200() throws Exception {
		List<CineProjectionNombre> lcine = Arrays.asList(this.cineProjectionNombre);
		when(cDao.getAllCineProjectionNombre()).thenReturn(lcine);
		ResponseEntity<Map<String, Object>> response = cineController.leerDirecciones();
		
		// Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals(lcine, response.getBody().get("data"));
	}
	
	
	
	@Test
	void whenGetOneExist_thenReturns200() throws Exception {
		when(cDao.leerUno(1L)).thenReturn(cineOptional);
		ResponseEntity<Map<String, Object>> response = cineController.leerUno(1L);
		
		// Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals(convertirAJson(cineDTO), response.getBody().get("data"));
	}
	
	@Test
	void whenGetOneNotExist_thenReturns200() throws Exception {
		when(cDao.leerUno(1L)).thenReturn(cineOptional);

		ResponseEntity<Map<String, Object>> response = cineController.leerUno(1L);
		
		// Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("status"));
        assertEquals(cineDTO, response.getBody().get("data"));
	}
	
	@Test
	void whenPostOk_thenReturns200() throws Exception {
		when(cDao.insert(this.cine)).thenReturn(this.cine);
		ResponseEntity<Map<String, Object>> response = cineController.alta(cineDTO);
	}
	
	@Test
	void whenPostErrorNombre_thenReturns400() throws Exception {
		when(cDao.insert(cineErrorNombre)).thenReturn(cineErrorNombre);
		ResponseEntity<Map<String, Object>> response = cineController.alta(cineDTO);
	}
	
	@Test
	void whenPostErrorCalle_thenReturns400() throws Exception {
		when(cDao.insert(cineErrorCalle)).thenReturn(cineErrorCalle);
		ResponseEntity<Map<String, Object>> response = cineController.alta(cineDTO);
	}
	
	@Test
	void whenPostErrorCapacidad_thenReturns400() throws Exception {
		when(cDao.insert(cineErrorCapacidad)).thenReturn(cineErrorCapacidad);
		ResponseEntity<Map<String, Object>> response = cineController.alta(cineDTO);
	}
	
	@Test
	void whenPutOk_thenReturns200() throws Exception {
		when(cDao.update(any(Cine.class))).thenReturn(true);
		System.out.println("*********************************************");
		System.out.println(this.cineExistente);
		ResponseEntity<Map<String, Object>> response = cineController.modificacion(cineDTO);

	}
	
	@Test
	void whenPutError_thenReturns400() throws Exception {
		when(cDao.update(any(Cine.class))).thenReturn(true);
		ResponseEntity<Map<String, Object>> response = cineController.modificacion(cineDTO);
	}
	
	@Test
	void whenPutErrorUpdate_thenReturns400() throws Exception {
		when(cDao.update(any(Cine.class))).thenReturn(false);
		ResponseEntity<Map<String, Object>> response = cineController.modificacion(cineDTO);
	}
	
	@Test
	void whenDeleteOk_thenReturns200() throws Exception {
		when(cDao.deleteById(1L)).thenReturn(true);
		ResponseEntity<Map<String, Object>> response = cineController.eliminar(1L);
	}
	
	@Test
	void whenDeleteError_thenReturns400() throws Exception {
		when(cDao.deleteById(1L)).thenReturn(false);
		ResponseEntity<Map<String, Object>> response = cineController.eliminar(1L);
	}
	
	
	public String convertirAJson(CineDTO cine) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(cine);
	}
}
