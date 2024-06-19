package com.recursosformacion.lcs.controller;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.model.dto.CineDTO;
import com.recursosformacion.lcs.model.dto.CineProjectionNombre;
import com.recursosformacion.lcs.persistence.entity.Cine;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.util.Constantes;



@WebMvcTest(controllers = CineController.class)
class CineControllerTestSpring {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private CineService cDao;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	CineDTO cineDTO;
	Cine cine;
	CineDTO cineExistente;
	CineDTO cineErrorNombre;
	CineDTO cineErrorCalle;
	CineDTO cineErrorCapacidad;
	
	String cineJson;
	
	Optional<Cine> cineOptional;
	CineProjectionNombre cineProjectionNombre;
	
	@BeforeEach
	void setup() throws JsonProcessingException {
		this.cine = new Cine(1L, "Cine1", "Calle 1","Barrio 1", 300,null);
		this.cineExistente = new CineDTO(10L, "Cine10", "Calle 10","Barrio 10", 500,null);
		this.cineErrorNombre = new CineDTO(1L, "", "Calle 1","Barrio 1", 300, null);	
		this.cineErrorCalle = new CineDTO(1L, "Cine1", "","Barrio 1", 300, null);
		this.cineErrorCapacidad = new CineDTO(1L, "Cine1", "Calle 1","Barrio 1", 0, null);
		this.cineDTO = new CineDTO(1L, "Cine1", "Calle 1","Barrio 1", 300,null);
		this.cineOptional = Optional.of(cine);
		this.cineProjectionNombre = new CineProjectionNombre(1L, "Cine1", "Barrio 1");
		this.cineJson = this.objectMapper.writeValueAsString(cine);
	}
		
	@Test
	void leoExistente_devuelve200() throws Exception {
		when(cDao.existe(1L)).thenReturn(true);
		when(cDao.leerUno(1L)).thenReturn(cineOptional);
		
		// Verificar
		mvc.perform(get("/api/cine/1"))
//				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
                .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_nombre", is(cine.getCi_nombre())))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_calle", is(cine.getCi_calle())))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_barrio", is(cine.getCi_barrio())))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_capacidad", is(cine.getCi_capacidad())))
                ;
		
	}
	
////	
	@Test
	void leoNoExistente_devuelveError() throws Exception {
		when(cDao.existe(1L)).thenReturn(false);
		mvc.perform(get("/api/cine/1"))
				.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
				.andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"leerUno.id\"]", containsString(Constantes.MSJ_ERROR_CINE_N.substring(0, 10))));
	}
	
	@Test
	void leeTodos_devuelve200() throws Exception {
		List<Cine> lcine = Arrays.asList(this.cine);
		when(cDao.listarTodos())
			.thenReturn(lcine);
		mvc.perform(get("/api/cine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
                .andExpect(jsonPath("$."+Constantes.DATOS+"[0].ci_nombre", is(cine.getCi_nombre())))
                .andExpect(jsonPath("$."+Constantes.DATOS+"[0].ci_calle", is(cine.getCi_calle())))
                .andExpect(jsonPath("$."+Constantes.DATOS+"[0].ci_barrio", is(cine.getCi_barrio())))
                .andExpect(jsonPath("$."+Constantes.DATOS+"[0].ci_capacidad", is(cine.getCi_capacidad())))
                ;	
	}
	
	@Test
	void leerTodosError() throws Exception {
		List<Cine> lista = new ArrayList<Cine>();
		when(cDao.listarTodos()).thenReturn(lista);
		mvc.perform(get("/api/cine"))
			      .andExpect(status().is4xxClientError())
			      .andExpect(jsonPath("$."+Constantes.STATUS, is(0)))
			      .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString(Constantes.MSJ_NO_EXISTEN_DATOS)))
			      ;
//				System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	void leeCineProyection_devuelve200() throws Exception {
		List<CineProjectionNombre> lcine = Arrays.asList(this.cineProjectionNombre);
		when(cDao.getAllCineProjectionNombre()).thenReturn(lcine);

		mvc.perform(get("/api/cine/direccion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
                .andExpect(jsonPath("$."+Constantes.DATOS+"[0].ci_nombre", is(cine.getCi_nombre())))
                .andExpect(jsonPath("$."+Constantes.DATOS+"[0].ci_barrio", is(cine.getCi_barrio())))
        ;
	}
	
	@Test
	void leeCineProyectionError() throws Exception {
		List<CineProjectionNombre> lista = new ArrayList<CineProjectionNombre>();
		when(cDao.getAllCineProjectionNombre()).thenReturn(lista);
		mvc.perform(get("/api/cine"))
			      .andExpect(status().is4xxClientError())
			      .andExpect(jsonPath("$."+Constantes.STATUS, is(0)))
			      .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString(Constantes.MSJ_NO_EXISTEN_DATOS)))
			      ;
	}
	
	@Test
	void hace_PostOk_devuelve201() throws Exception {
		when(cDao.insert(any(Cine.class))).thenReturn(this.cine);
		mvc.perform(post("/api/cine")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(cineDTO)))
		
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_nombre", is(cine.getCi_nombre())))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_calle", is(cine.getCi_calle())))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_barrio", is(cine.getCi_barrio())))
                .andExpect(jsonPath("$."+Constantes.DATOS+".ci_capacidad", is(cine.getCi_capacidad())))
                ;
	}
	
	@Test
	void hace_PostErrorNombre_devuelveControllerException() throws Exception {
		//when(cDao.insert(any(Cine.class))).thenThrow(ConstraintViolationException.class);
		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineErrorNombre)))
		
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
        .andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"ci_nombre\"]", containsString("must not be empty")));
	}
	
	@Test
	void hace_PostErrorCalle_devuelveControllerException() throws Exception {
		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineErrorCalle)))
		
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
        .andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"ci_calle\"]", containsString("must not be empty")));
	}
	
	@Test
	void hace_PostErrorCapacidad_devuelveControllerException() throws Exception {
		mvc.perform(post("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineErrorCapacidad)))
		
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
        .andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"ci_capacidad\"]").exists());
	}

	@Test
	void hace_PutOk_devuelve200() throws Exception {
		when(cDao.update(any(Cine.class))).thenReturn(cine);
		mvc.perform(put("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cineDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))

        .andExpect(jsonPath("$."+Constantes.DATOS+".ci_nombre", is(cine.getCi_nombre())))
        .andExpect(jsonPath("$."+Constantes.DATOS+".ci_calle", is(cine.getCi_calle())))
        .andExpect(jsonPath("$."+Constantes.DATOS+".ci_barrio", is(cine.getCi_barrio())))
        .andExpect(jsonPath("$."+Constantes.DATOS+".ci_capacidad", is(cine.getCi_capacidad())))
        ;
	}

	
	@Test
	void hace_PutError_devuelveErrorNoExiste() throws Exception {
		cine.setId_cine(99999L);
		cineJson = this.objectMapper.writeValueAsString(cine);
		when(cDao.existe(any(Long.class))).thenReturn(false);
		when(cDao.update(any(Cine.class))).thenReturn(null);
		mvc.perform(put("/api/cine")
				.contentType(MediaType.APPLICATION_JSON)
				.content(cineJson))
		.andDo(MockMvcResultHandlers.print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(0)))
        .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString(Constantes.MSJ_ERROR_UPDATE)));
	}

	@Test
	void hace_DeleteOk_devuelve200() throws Exception {
		when(cDao.existe(1L)).thenReturn(true);
		when(cDao.borrarPorId(1L)).thenReturn(true);
		mvc.perform(delete("/api/cine/1"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$."+Constantes.STATUS, is(1)))
		        .andExpect(jsonPath("$."+Constantes.MENSAJE, containsString(Constantes.MSJ_ELIMINACION_OK)));
	}
	
	@Test
	void hace_DeleteError_devuelveControllerException() throws Exception {
		when(cDao.existe(1L)).thenReturn(false);
		mvc.perform(get("/api/cine/1"))
		.andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$."+Constantes.STATUS, is(900)))
		.andExpect(jsonPath("$."+Constantes.MENSAJE+"[\"leerUno.id\"]", containsString(Constantes.MSJ_ERROR_CINE_SN)));

	
		
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
	 * Compara dos objetos Cine devolviendo true/false
	 * @return
	 */
	public boolean compararCines(Cine cine, Cine other) {
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
	            return false; 
	        } 
		}
		return true;	
	}
}