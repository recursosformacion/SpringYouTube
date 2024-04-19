package com.recursosformacion.lcs.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.repository.ICine;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.util.constraint.validator.CheckCineValidator;

import jakarta.validation.ConstraintViolationException;

@WebMvcTest(controllers = CineController.class)
class CineControllerTestMock {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	

	@MockBean
	private CineService service;
	

	@BeforeEach
	void setup() {
		
	}

	// Comprobando que esta activo
	@Test
	void whenGetAll_thenReturns200() throws Exception {
		mvc.perform(get("/api/cine")).andExpect(status().isOk());
	}

	// Comprobando que esta activo por direccion
	@Test
	void whenGetAll_direction_thenReturns200() throws Exception {
		mvc.perform(get("/api/cine/direccion")).andExpect(status().isOk());
	}

	// comprobando recepcion de parametros
	@Test
	void whenAdd_ValidInput_thenReturns200() throws Exception {
		Cine cine = new Cine(0, "Cine1", "Calle 1", 300);
		String json = objectMapper.writeValueAsString(cine);
		mvc.perform(post("/api/cine")
				.contentType("application/json")
				.content(json))
				.andExpect(status().isOk());
	}

	@Test
	void whenPathVariableIsValid_thenReturnsStatus200() throws Exception {
		mvc.perform(get("/api/cine/4952")).andExpect(status().isOk());
	}

	
	@Test
	void whenCineIsInvalid_thenThrowsException() {
		Cine cine = new Cine(0, "Cine1", "", 3300);
		assertThrows(ConstraintViolationException.class, () -> {
		      service.validateInput(cine);
		    });
	}
		
	@Test
	void whenRequestUrl_ParameterIsInvalid_thenReturnsStatus422() throws Exception {
		mvc.perform(get("/api/cine/3")).andExpect(status().is(422));
	}
	
	@Test
	void whenPathVariableIsInvalid_thenReturnsStatus422() throws Exception {
		mvc.perform(get("/api/cine/1")).andExpect(status().is(422));
	}
}
