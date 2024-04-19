package com.recursosformacion.lcs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.model.dto.EntradaDTO;
import com.recursosformacion.lcs.util.Constantes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class EntradaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
	ObjectMapper objectMapper;
    
    final LocalDate AHORA = LocalDate.now();
	final LocalDate MANIANA = LocalDate.now().plusDays(1);;
	final LocalDate AYER = LocalDate.now().minusDays(1);
	
    EntradaDTO entradaOk;
    EntradaDTO entradaErrorDNI;
    EntradaDTO entradaErrorFecha;
    EntradaDTO entradaErrorFila	;
    EntradaDTO entradaErrorNumero;
    EntradaDTO entradaErrorCine;
    
   
    @BeforeEach
	void setup() {
    	entradaOk = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 10L);
    	this.entradaErrorDNI = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-B" , 10L);
    	this.entradaErrorFecha = new EntradaDTO(1L, AYER.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 10L);
    	this.entradaErrorFila = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 0, 20, "56.789.012-A" , 10L);
    	this.entradaErrorNumero = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 0, "56.789.012-A" , 10L);
    	this.entradaErrorCine = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 999L);
    	
    }

    @Test
    void testLeerUno() throws Exception {
        String id = "1";

        mockMvc.perform(get("/api/entrada/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.id_entrada", is(1)));
    }

    @Test
    void testLeerTodos() throws Exception {
        mockMvc.perform(get("/api/entrada")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)));
    }

    @Test
    void testLeerPorId() throws Exception {
        String idCliente = "1";

        mockMvc.perform(get("/api/entrada/leerporid/" + idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)));
    }

    @Test
    void testLeerPorCine() throws Exception {
        String idCine = "10";

        mockMvc.perform(get("/api/entrada/leerporcine/" + idCine)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)));
    }

    @Test
    void testAlta() throws Exception {
        String entradaJson = objectMapper.writeValueAsString(entradaOk);

        mockMvc.perform(post("/api/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entradaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.id_entrada", is(1)));
    }
    
    void comprobarDeteccionDniErroneo() throws Exception {
    	String entradaJson = objectMapper.writeValueAsString(entradaErrorDNI);
    	
	    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/entrada")
	    		.accept(MediaType.TEXT_HTML)
	    		.content(entradaJson))
	            .andExpect(status().is4xxClientError());
	}

    @Test
    void testModificacion() throws Exception {
        String entradaJson =  objectMapper.writeValueAsString(entradaOk);

        mockMvc.perform(put("/api/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entradaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.message", is("Actualizacion realizada")));
    }

    @Test
    void testEliminar() throws Exception {
        String id = "1";

        mockMvc.perform(delete("/api/entrada/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.message", is("Registro borrado")));
    }
}