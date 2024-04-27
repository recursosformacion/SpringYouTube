package com.recursosformacion.lcs.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.persistence.entity.CineTest;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.util.Constantes;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CineControllerTestIntegracion {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CineService cDao;

	ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger LOGGER = LogManager.getLogger(CineControllerTestIntegracion.class);

	CineTest cine;
	CineTest cine2;
	CineTest cine3;
	CineTest cineExistente;
	CineTest cineErrorNombre;
	CineTest cineErrorCalle;
	CineTest cineErrorCapacidad;
	String cineJson;
	String cine2Json;
	String cine3Json;
	String cineExistenteJson;
	String cineErrorNombreJson;
	String cineErrorCalleJson;
	String cineErrorCapacidadJson;

	Optional<CineTest> cineOptional;
	CineProjectionNombre cineProjectionNombre;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setup() throws JsonProcessingException, ConstraintViolationException, DAOException, DomainException {

		this.cine = new CineTest(100L, "CineTest1", "Calle 1", "Barrio 1", 300, null);
		this.cine2 = new CineTest(101L, "CineTest2", "Calle 2", "Barrio 2", 400, null);
		this.cine3 = new CineTest(102L, "CineTest3", "Calle 3", "Barrio 3", 500, null);

		this.cineJson = objectMapper.writeValueAsString(cine);
		this.cine2Json = objectMapper.writeValueAsString(cine2);
		this.cine3Json = objectMapper.writeValueAsString(cine3);
		this.cineExistente = new CineTest(10L, "CineTest10", "Calle 10", "Barrio 10", 500, null);
		this.cineExistenteJson = this.objectMapper.writeValueAsString(cineExistente);
		this.cineErrorNombre = new CineTest(200L, "", "Calle 1", "Barrio 1", 300, null);
		this.cineErrorCalle = new CineTest(201L, "CineTest1", "", "Barrio 1", 300, null);
		this.cineErrorCapacidad = new CineTest(202L, "CineTest1", "Calle 1", "Barrio 1", 0, null);
		this.cineErrorNombreJson = this.objectMapper.writeValueAsString(cineErrorNombre);
		this.cineErrorCalleJson = this.objectMapper.writeValueAsString(cineErrorCalle);
		this.cineErrorCapacidadJson = this.objectMapper.writeValueAsString(cineErrorCapacidad);

		this.cineOptional = Optional.of(cine);
		this.cineProjectionNombre = new CineProjectionNombre(500L, "CineTest1", "Barrio 1");

	}

	@Test
	void getAllCineTestAPI() throws Exception {
		MvcResult result = mvc.perform(get("/api/cine")).andReturn();

		LOGGER.info("****************************************************************");
		convertirAStream(result).forEach(cine -> {
			LOGGER.info(cine);
		});
		LOGGER.info("****************************************************************");

		mvc.perform(get("/api/cine").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.data[*].id_cine").isNotEmpty());
	}

	@Test
	void testLeerDirecciones() throws Exception {
		mvc.perform(get("/api/cine/direccion")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data")
			.exists())
			.andExpect(jsonPath("$.data[*].id_cine")
			.isNotEmpty());
	}

//	
	@Test
	void testLeerUno() throws Exception {
		String id = "16";

		mvc.perform(get("/api/cine/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(1))).andExpect(jsonPath("$.data.id_cine", is(16)));
	}

//	 
	@Test
	void testLeerUno_fallando() throws Exception {
		String id = "9999";

		mvc.perform(get("/api/cine/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andExpect(jsonPath("$.status", is(900)));
	}

	@Test
	void testAlta() throws Exception {

		mvc.perform(post("/api/cine").contentType(MediaType.APPLICATION_JSON).content(cine2Json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.message", is("Registro salvado")));
	}

	@Test
	void testModificacion() throws Exception {

		mvc.perform(put("/api/cine").contentType(MediaType.APPLICATION_JSON).content(cineExistenteJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.message", is("Actualizacion correcta")));
	}

	@Test
	public void testEliminar() throws Exception {

		String id = "10";
		mvc.perform(delete("/api/cine/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(1))).andExpect(jsonPath("$.message", is("Registro borrado")));

		id = "10";
		mvc.perform(get("/api/cine/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andExpect(jsonPath("$.status", is(900)));
	}

	Stream<Cine> convertirAStream(MvcResult mvcResult)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		String contentAsString = mvcResult.getResponse().getContentAsString();

		// Lee el valor del campo 'data' en el JSON y conviértelo a una lista de Cine
		JsonNode root = objectMapper.readTree(contentAsString);

		ObjectMapper objectMapper = new ObjectMapper();

		// Configura ObjectMapper para convertir automáticamente los objetos JSON en
		// objetos Cine
		CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Cine.class);
		List<Cine> list = objectMapper.readValue(root.get(Constantes.DATOS).toString(), type);

		return list.stream();
	}
}
