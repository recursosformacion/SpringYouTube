package com.recursosformacion.lcs.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.service.CineService;


@WebMvcTest(controllers = EntradaController.class)
class CineControllerTest {
	
	@Mock
	private CineService service;
	
	@Autowired
	private CineController cineController;
	
	
	ObjectMapper objectMapper = new ObjectMapper();
	
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
		when(service.existsById(1L)).thenReturn(true);
		when(service.existsById(1000L)).thenReturn(false);
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
		when(service.listAll()).thenReturn(lcine);
		mvc.perform(get("/api/cine"))
			.andExpect(status()
					.isOk());
	}

	// Comprobando que esta activo por direccion
	@Test
	void whenGetAll_direction_thenReturns200() throws Exception {
		List<CineProjectionNombre> lcine = Arrays.asList(this.cineProjectionNombre);
		when(service.getAllCineProjectionNombre()).thenReturn(lcine);
		mvc.perform(get("/api/cine/direccion")
				.contentType("application/json")

				)
			.andExpect(status().isOk());
	}
	
	
	
	@Test
	void whenGetOneExist_thenReturns200() throws Exception {
		when(service.leerUno(1L)).thenReturn(cineOptional);
		mvc.perform(get("/api/cine/1")).andExpect(status().isOk());
	}
	
	@Test
	void whenGetOneNotExist_thenReturns200() throws Exception {
		when(service.leerUno(1000L)).thenReturn(cineOptional);
		mvc.perform(get("/api/cine/1000")).andExpect(status().is(422));
	}
	
	@Test
	void whenPostOk_thenReturns200() throws Exception {
		when(service.insert(this.cine)).thenReturn(this.cine);
		mvc.perform(post("/api/cine")
				.contentType("application/json")
				.content(this.cineJson))
				.andExpect(status().isOk());
	}
	
	@Test
	void whenPostErrorNombre_thenReturns400() throws Exception {
		when(service.insert(cineErrorNombre)).thenReturn(cineErrorNombre);
		mvc.perform(post("/api/cine")
				.contentType("application/json")
				.content(this.cineErrorNombreJson))
				.andExpect(status().is(400));
	}
	
	@Test
	void whenPostErrorCalle_thenReturns400() throws Exception {
		when(service.insert(cineErrorCalle)).thenReturn(cineErrorCalle);
		mvc.perform(post("/api/cine")
				.contentType("application/json")
				.content(this.cineErrorCalleJson))
				.andExpect(status().is(400));
	}
	
	@Test
	void whenPostErrorCapacidad_thenReturns400() throws Exception {
		when(service.insert(cineErrorCapacidad)).thenReturn(cineErrorCapacidad);
		mvc.perform(post("/api/cine")
				.contentType("application/json")
				.content(this.cineErrorCapacidadJson))
				.andExpect(status().is(400));
	}
	
	@Test
	void whenPutOk_thenReturns200() throws Exception {
		when(service.update(any(Cine.class))).thenReturn(true);
		System.out.println("*********************************************");
		System.out.println(this.cineExistente);
		mvc.perform(put("/api/cine")
				.contentType("application/json")
				.content(this.cineExistenteJson))
				.andExpect(status().is(200));
	}
	
	@Test
	void whenPutError_thenReturns400() throws Exception {
		when(service.update(any(Cine.class))).thenReturn(true);
		mvc.perform(put("/api/cine")
				.contentType("application/json")
				.content(this.cineErrorNombreJson))
				.andExpect(status().is(400));
	}
	
	@Test
	void whenPutErrorUpdate_thenReturns400() throws Exception {
		when(service.update(any(Cine.class))).thenReturn(false);
		mvc.perform(put("/api/cine")
				.contentType("application/json")
				.content(this.cineErrorNombreJson))
				.andExpect(status().is(400));
	}
	
	@Test
	void whenDeleteOk_thenReturns200() throws Exception {
		when(service.deleteById(1L)).thenReturn(true);
		mvc.perform(delete("/api/cine/1"))
				.andExpect(status().is(200));
	}
	
	@Test
	void whenDeleteError_thenReturns400() throws Exception {
		when(service.deleteById(1000L)).thenReturn(true);
		mvc.perform(delete("/api/cine/1000"))
				.andExpect(status().is(422));
	}
	
}
