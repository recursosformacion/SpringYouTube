package com.recursosformacion.lcs.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.persistence.entity.Pelicula;
import com.recursosformacion.lcs.service.PeliculaService;
import com.recursosformacion.lcs.util.Constantes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = PeliculaController.class)
class PeliculaControllerTestSpring {

	@Autowired
    private MockMvc mockMvc;
    
    @Autowired
	ObjectMapper mapper;
    
    @MockBean
    private PeliculaService cDao;
        
    static final String STATUS = "$."+Constantes.STATUS;
    static final String DATOS = "$."+Constantes.DATOS;
    static final String MENSAJE = "$."+Constantes.MENSAJE;
    static final String RUTA = "/api/pelicula";
    static final String RUTAb = "/api/pelicula/";
    static final int NUMERO_REGISTROS = 3;
	
    Pelicula pelicula;
    Pelicula peliculaOk;
    Pelicula peliculaError;

    
    Optional<Pelicula> peliculaOptional;
    
    List<Pelicula> listaPeliculas;
    
    @BeforeAll
	static void init() {

	}
   
    @BeforeEach
	void setup() {
    	
    	
    	peliculaOk = new Pelicula(1L, "La pelicula correcta", 123);
    	this.peliculaError = new Pelicula(1L, null, 456);
    	
    	this.pelicula = new Pelicula(2, "Una pelicula",789);
    	this.peliculaOptional = Optional.of(pelicula);
    	
    	this.listaPeliculas = Arrays.asList(pelicula, pelicula,pelicula);
    }

    @Test
    void testLeerUno() throws Exception {
        when(cDao.leerUno(1L)).thenReturn(peliculaOptional);     
        when(cDao.existe(1L)).thenReturn(true);
        mockMvc.perform(get(RUTAb + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(DATOS + ".id_pelicula", is(2)))
                .andExpect(jsonPath(DATOS + ".pe_titulo", is("Una pelicula")))
                .andExpect(jsonPath(DATOS + ".pe_identificador", is(789)))
                ;
    }
    
    @Test
    void testLeerUno_error() throws Exception {
        Long id = 1L;
        when(cDao.existe(any(Long.class))).thenReturn(false);
        mockMvc.perform(get(RUTAb + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(STATUS, is(900)))
                .andExpect(jsonPath(MENSAJE+"[\"leerUno.id\"]",  containsString(Constantes.MSJ_ERROR_PELICULA_N)));
    }

    @Test
    void testLeerTodos() throws Exception {
    	
    	when(cDao.listarTodos()).thenReturn(listaPeliculas);
        mockMvc.perform(get(RUTA)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(DATOS+".length()", is(NUMERO_REGISTROS))	
                );
    }

    @Test
    void testLeerTodos_error() throws Exception {
    	
    	when(cDao.listarTodos()).thenReturn(new ArrayList<Pelicula>());
        mockMvc.perform(get(RUTA)
                .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().is4xxClientError())
	      .andExpect(jsonPath(STATUS, is(0)))
	      .andExpect(jsonPath(MENSAJE, containsString(Constantes.MSJ_NO_EXISTEN_DATOS)))
	
                ;
    }

    @Test
    void testAlta() throws Exception {
        String peliculaJson = mapper.writeValueAsString(peliculaOk);
        when(cDao.insert(any(Pelicula.class))).thenReturn(pelicula);

        mockMvc.perform(post(RUTA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(peliculaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(STATUS, is(1)))
 //               .andExpect(jsonPath(DATOS + ".id_pelicula", is(pelicula.getId_pelicula())))
                .andExpect(jsonPath(DATOS + ".pe_titulo", is(pelicula.getPe_titulo())))
                .andExpect(jsonPath(DATOS + ".pe_identificador", is(pelicula.getPe_identificador())));
    }
    
    @Test
    void testAltaErrorTitulo() throws Exception {
    	String peliculaJson = mapper.writeValueAsString(peliculaError);
    	
	    this.mockMvc.perform(post(RUTA)
	    		.accept(MediaType.TEXT_HTML)
	    		.content(peliculaJson))
	            .andExpect(status().is4xxClientError());
	}

    @Test
    void testModificacion() throws Exception {
        String peliculaJson =  mapper.writeValueAsString(peliculaOk);
        when(cDao.update(any(Pelicula.class))).thenReturn(peliculaOk);

        mockMvc.perform(put(RUTA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(peliculaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(DATOS + ".pe_titulo", is(peliculaOk.getPe_titulo())))
                .andExpect(jsonPath(DATOS + ".pe_identificador", is(peliculaOk.getPe_identificador())));
    }
    
    
    @Test
    void testModificacionErrorTitulo() throws Exception {
    	String peliculaJson = mapper.writeValueAsString(peliculaError);
    	
	    this.mockMvc.perform(put(RUTA)
	    		.accept(MediaType.TEXT_HTML)
	    		.content(peliculaJson))
	            .andExpect(status().is4xxClientError());
	}

    @Test
    void testEliminar() throws Exception {
        Long id = 1L;
        when(cDao.existe(id)).thenReturn(true);
        when(cDao.borrarPorId(any(Long.class))).thenReturn(true);

        mockMvc.perform(delete(RUTAb + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(MENSAJE, containsString (Constantes.MSJ_ELIMINACION_OK)));
    }
    
    @Test
    void testEliminarError() throws Exception {
        Long id = 1L;
        when(cDao.existe(1L)).thenReturn(false);

        mockMvc.perform(delete(RUTAb + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(STATUS, is(900)))
                .andExpect(jsonPath(MENSAJE+"[\"borrar.id\"]", containsString (Constantes.MSJ_ERROR_PELICULA_N)));
    }
}