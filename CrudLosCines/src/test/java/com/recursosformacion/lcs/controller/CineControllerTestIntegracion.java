package com.recursosformacion.lcs.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.util.Constantes;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class CineControllerTestIntegracion {

	@Autowired
	private MockMvc mvc;

	ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger LOGGER = LogManager.getLogger(CineControllerTestIntegracion.class);
	
	final String STATUS = "$."+Constantes.STATUS;
	final String DATOS = "$."+Constantes.DATOS;
	final String MENSAJE = "$."+Constantes.MENSAJE;
	final int NUMERO_REGISTROS = 7;


	Cine cine17;
	Cine cine16;
	Cine cineExistente;
	Cine cineErrorNombre;
	Cine cineErrorCalle;
	Cine cineErrorCapacidad;
	String cineJson;
	String cine17Json;
	String cine16Json;
	String cineExistenteJson;
	String cineErrorNombreJson;
	String cineErrorCalleJson;
	String cineErrorCapacidadJson;

	Optional<Cine> cineOptional;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setup() throws JsonProcessingException, ConstraintViolationException, DAOException, DomainException {

		this.cine17 = new Cine(17L, "cine17", "Calle 2", "Barrio 2", 400, null);
		this.cine16 = new Cine(16L, "Poliorama", "Avda Los cines 123", "Peliculero", 450, null);


		this.cine17Json = objectMapper.writeValueAsString(cine17);
		this.cine16Json = objectMapper.writeValueAsString(cine16);
		this.cineExistente = new Cine(12L, "Modificando Nombre", "Calle 10", "Barrio 10", 500, null);
		this.cineExistenteJson = this.objectMapper.writeValueAsString(cineExistente);
		this.cineErrorNombre = new Cine(200L, "", "Calle 1", "Barrio 1", 300, null);
		this.cineErrorCalle = new Cine(201L, "Cine1", "", "Barrio 1", 300, null);
		this.cineErrorCapacidad = new Cine(202L, "Cine1", "Calle 1", "Barrio 1", 0, null);
		this.cineErrorNombreJson = this.objectMapper.writeValueAsString(cineErrorNombre);
		this.cineErrorCalleJson = this.objectMapper.writeValueAsString(cineErrorCalle);
		this.cineErrorCapacidadJson = this.objectMapper.writeValueAsString(cineErrorCapacidad);

		this.cineOptional = Optional.of(cine17);

	}
//	
	@Test
	@Order(20)
	void leoExistente_devuelve200() throws Exception {
		String id = "16";

		mvc.perform(get("/api/cine/" + id)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
            .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
            .andExpect(jsonPath("$."+Constantes.DATOS+".ci_nombre", is(cine16.getCi_nombre())))
            .andExpect(jsonPath("$."+Constantes.DATOS+".ci_calle", is(cine16.getCi_calle())))
            .andExpect(jsonPath("$."+Constantes.DATOS+".ci_barrio", is(cine16.getCi_barrio())))
            .andExpect(jsonPath("$."+Constantes.DATOS+".ci_capacidad", is(cine16.getCi_capacidad()))
            		);
	}

//	 
	@Test
	@Order(21)
	void leoNoExistente_devuelveError() throws Exception {
		String id = "9999";

		mvc.perform(get("/api/cine/" + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath(STATUS, is(900)))
				.andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"leerUno.id\"]", containsString(Constantes.MSJ_ERROR_CINE_SN)));
		;
	}

	@Test
	@Order(1)
	void leeTodos_devuelve200() throws Exception {
//		listaTabla("leeTodos_devuelve200");
		mvc.perform(get("/api/cine")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath(DATOS).exists())
			.andExpect(jsonPath(STATUS, is(1)))
			.andExpect(jsonPath(DATOS+".size()",is(NUMERO_REGISTROS)))
			.andExpect(jsonPath(DATOS+"[*].id_cine").isNotEmpty())
			.andExpect(jsonPath(DATOS+"[*].ci_nombre").isNotEmpty())
			.andExpect(jsonPath(DATOS+"[*].ci_calle").isNotEmpty())
			//se incorpora esta línea con fines didácticos, pero si no se
			//ejecuta en orden, los resultados son imprevisibles
			.andExpect(jsonPath(DATOS+"[*].id_cine",containsInAnyOrder(10,11,12,13,14,15,16)))
			;
	}
	
	@Test
	@Order(11)
	void leeCineProyection_devuelve200() throws Exception {

		mvc.perform(get("/api/cine/direccion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
    			.andExpect(jsonPath(DATOS+"[*].ci_nombre").isNotEmpty())
                .andExpect(jsonPath(DATOS+"[*].ci_barrio").isNotEmpty())
        ;
	}
	
	@Test
	@Order(22)
	void hace_PostOk_devuelve201() throws Exception {
		MvcResult result = mvc.perform(get("/api/cine")).andReturn();
		long registros =convertirAStream(result).count();

		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(cine17Json))
			.andExpect(status().isCreated())
			.andExpect(jsonPath(STATUS, is(1)))
			.andExpect(jsonPath(DATOS+".ci_nombre").isNotEmpty())
            .andExpect(jsonPath(DATOS+".ci_barrio").isNotEmpty());

		mvc.perform(get("/api/cine")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath(DATOS+".size()",is(registros+1),Long.class))
			;
	}
	
	@Test
	@Order(23)
	void hace_PostErrorNombre_devuelveMensajeError() throws Exception {
		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineErrorNombre)))
		
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath(STATUS, is(900)))
        .andExpect(jsonPath(MENSAJE+"[\"ci_nombre\"]", containsString("must not be empty")));
	}

	@Test
	@Order(24)
	void hace_PostErrorCalle_devuelveControllerException() throws Exception {
		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineErrorCalle)))
		
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath(STATUS, is(900)))
        .andExpect(jsonPath(MENSAJE+"[\"ci_calle\"]", containsString("must not be empty")));
	}
	
	@Test
	@Order(25)
	void hace_PostErrorCapacidad_devuelveControllerException() throws Exception {
		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineErrorCapacidad)))
		
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
        .andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"ci_capacidad\"]").exists());
	}

	@Test
	@Order(26)
	void hace_PutOk_devuelve200() throws Exception {
		
		mvc.perform(put("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(cineExistenteJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath(STATUS, is(1)))
			.andExpect(jsonPath(DATOS+".ci_nombre").isNotEmpty())
            .andExpect(jsonPath(DATOS+".ci_barrio").isNotEmpty());
	
	    mvc.perform(get("/api/cine/12")
                .contentType(MediaType.APPLICATION_JSON))
	                    .andExpect(status().isOk())
	                    .andExpect(jsonPath(STATUS, is(1)))
	                    .andExpect(jsonPath(DATOS+".ci_nombre", is("Modificando Nombre")))
	                    .andExpect(jsonPath(DATOS+".ci_calle", is("Calle 10")))
	                    .andExpect(jsonPath(DATOS+".ci_barrio", is("Barrio 10")))
	                    .andExpect(jsonPath(DATOS+".ci_capacidad", is(500)))
	                    ;
	    }

	@Test
	@Order(27)
	void hace_PutError_devuelveErrorAlModificar() throws Exception {
		Cine cineDTO = new Cine(9999999L, "Cine10", "Calle 10", "Barrio 10", 500, null);

		mvc.perform(put("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineDTO)))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(0)))
        .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString("El registro ya no existe")));
	}
	
	@Test
	@Order(28)
	void hace_DeleteOk_devuelve200() throws Exception {

		String id = "10";
		mvc.perform(delete("/api/cine/" + id)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath(STATUS, is(1)))
			.andExpect(jsonPath(MENSAJE, is(Constantes.MSJ_ELIMINACION_OK)));

		id = "10";
		mvc.perform(get("/api/cine/" + id)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath(STATUS, is(900)));
	}

	@Test
	@Order(29)
	void hace_DeleteError_devuelveControllerException() throws Exception {
		
		mvc.perform(get("/api/cine/9999999"))
		.andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
		.andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"leerUno.id\"]", containsString(Constantes.MSJ_ERROR_CINE_SN)));

	
		
	}
	
	@Test
	@Order(900)
	void comprueba_error_lista_vacia() throws Exception {
		listaTabla("comprueba_error_lista_vacia");
		for (long i = 11; i <= 16; i++) {
			mvc.perform(delete("/api/cine/" + i)).andExpect(status().isOk()).andExpect(jsonPath(STATUS, is(1)))
					.andExpect(jsonPath(MENSAJE, is(Constantes.MSJ_ELIMINACION_OK)));
		}
		mvc.perform(delete("/api/cine/1")).andExpect(status().isOk()).andExpect(jsonPath(STATUS, is(1)))
		.andExpect(jsonPath(MENSAJE, is(Constantes.MSJ_ELIMINACION_OK)));
		
		mvc.perform(get("/api/cine"))
	      .andExpect(status().is4xxClientError())
	      .andExpect(jsonPath("$."+Constantes.STATUS, is(0)))
	      .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString(Constantes.MSJ_NO_EXISTEN_DATOS)))
		.andReturn()
	      ;
		
	}
	
	@Test
	@Order(910)
	void leeCineProyectionError() throws Exception {
		mvc.perform(get("/api/cine/direccion"))
			      .andExpect(status().is4xxClientError())
			      .andExpect(jsonPath("$."+Constantes.STATUS, is(0)))
			      .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString("No existen datos")))
			      ;
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
	
	void listaTabla(String rutina) throws Exception {
		MvcResult result = mvc.perform(get("/api/cine")).andReturn();

		LOGGER.info("**************"+ rutina + "**************************************************");
		convertirAStream(result).forEach(cine -> {
			LOGGER.info(cine);
		});
		LOGGER.info("****************************************************************");
	}
}
