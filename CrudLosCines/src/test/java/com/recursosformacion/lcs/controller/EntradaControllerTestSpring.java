package com.recursosformacion.lcs.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recursosformacion.lcs.model.dto.EntradaDTO;
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.service.EntradaService;
import com.recursosformacion.lcs.util.Constantes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = EntradaController.class)
class EntradaControllerTestSpring {

	@Autowired
    private MockMvc mockMvc;
    
    @Autowired
	ObjectMapper mapper;
    
    @MockBean
    private EntradaService cDao;
    @MockBean
    private CineService cineService;
        
    static final String STATUS = "$."+Constantes.STATUS;
    static final String DATOS = "$."+Constantes.DATOS;
    static final String MENSAJE = "$."+Constantes.MENSAJE;
    static final String RUTA = "/api/entrada";
    static final String RUTAb = "/api/entrada/";
    static final int NUMERO_REGISTROS = 7;
    
    static final LocalDate AHORA = LocalDate.now();
    static final LocalDate MANIANA = LocalDate.now().plusDays(1);;
    static final LocalDate AYER = LocalDate.now().minusDays(1);
    
    static  String DNI_OK;
    static  String DNI_ERR;
	
    Entrada entrada;
    EntradaDTO entradaOk;
    EntradaDTO entradaErrorDNI;
    EntradaDTO entradaErrorFecha;
    EntradaDTO entradaErrorFila	;
    EntradaDTO entradaErrorNumero;
    EntradaDTO entradaErrorCine;
    
    Optional<Entrada> entradaOptional;
    
    List<Entrada> listaEntradas;
    
    @BeforeAll
	static void init() {
		System.out.println("Inicio de las pruebas******************************");
		Random random = new Random();
        int numeroDni = random.nextInt(100000000) + 10000000;
        char letraDniOk = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numeroDni % 23);
        char letraDniErr = "TRWAGMYFPDXBNJZSQVHLCKE".charAt((numeroDni + 1) % 23);
        DNI_OK = String.format("%,d", numeroDni) + "-" + letraDniOk;
        DNI_ERR = String.format("%,d", numeroDni) + "-" + letraDniErr;
        if (numeroDni < 10000000) {
			DNI_OK = "0" + DNI_OK;
		}
        
        System.out.println("DNI:    " + DNI_OK);
        System.out.println("DNI_Err:" + DNI_ERR);
        
    	System.out.println("Fechas******************************");
    	System.out.println("Ahora: " + AHORA);
    	System.out.println("Mañana:" + MANIANA);
    	System.out.println("Ayer:  " +AYER);
    	System.out.println("Fechas******************************");
	}
   
    @BeforeEach
	void setup() {
    	
    	
    	entradaOk = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, DNI_OK, 10L);
    	this.entradaErrorDNI = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, DNI_ERR, 10L);
    	this.entradaErrorFecha = new EntradaDTO(1L, AYER.format(Constantes.FORMATO_FECHA_EU), 10, 20, DNI_OK , 10L);
    	this.entradaErrorFila = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 0, 20, DNI_OK , 10L);
    	this.entradaErrorNumero = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 0, DNI_OK, 10L);
    	this.entradaErrorCine = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, DNI_OK , 999L);
    	
    	this.entrada = new Entrada(1L, MANIANA, 10, 20, DNI_OK , 10L);
    	this.entradaOptional = Optional.of(entrada);
    	
    	this.listaEntradas = Arrays.asList(entrada, entrada,entrada,entrada,entrada);
    	when(cineService.existe(10L)).thenReturn(true);
    }

    @Test
    void testLeerUno() throws Exception {
        String id = "1";
        when(cDao.leerUno(1L)).thenReturn(entradaOptional);
        mockMvc.perform(get(RUTAb + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath("$.data.id_entrada", is(1)));
    }
    
    @Test
    void testLeerUno_error() throws Exception {
        Long id = 1L;
        when(cDao.leerUno(id)).thenReturn(Optional.empty());
        mockMvc.perform(get(RUTAb + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(STATUS, is(0)))
                .andExpect(jsonPath(MENSAJE, containsString(Constantes.MSJ_NO_EXISTEN_DATOS)));
    }
    
    

    @Test
    void testLeerTodos() throws Exception {
    	
    	when(cDao.listarTodos()).thenReturn(listaEntradas);
        mockMvc.perform(get(RUTA)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)));
    }

    @Test
    void testLeerPorId() throws Exception {
        String idCliente = "1";
        when(cDao.buscarPorIdCliente(any(String.class))).thenReturn(listaEntradas);
        mockMvc.perform(get(RUTAb + "leerporid/" + idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)));
    }

    @Test
    void testLeerPorCine() throws Exception {
        Long idCine = 1L;
        
        when(cDao.buscarPorEntCine(any())).thenReturn(listaEntradas);

        mockMvc.perform(get(RUTAb + "leerporcine/" + idCine)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)));
    }

    @Test
    void testAlta() throws Exception {
        String entradaJson = mapper.writeValueAsString(entradaOk);
        System.out.println("entradaJson-" + entradaJson);
        System.out.println("entradaOk-" + entradaOk);
        when(cDao.insert(any(Entrada.class))).thenReturn(entrada);

        mockMvc.perform(post(RUTA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(entradaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(DATOS + ".id_entrada", is(1)));
    }
    
    @Test
    void comprobarDeteccionDniErroneo() throws Exception {
    	String entradaJson = mapper.writeValueAsString(entradaErrorDNI);
    	
	    this.mockMvc.perform(MockMvcRequestBuilders.post(RUTA)
	    		.accept(MediaType.TEXT_HTML)
	    		.content(entradaJson))
	            .andExpect(status().is4xxClientError());
	}

    @Test
    void testModificacion() throws Exception {
        String entradaJson =  mapper.writeValueAsString(entradaOk);
        when(cDao.update(any(Entrada.class))).thenReturn(entrada);

        mockMvc.perform(put(RUTA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(entradaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(DATOS + ".id_entrada", is(1)))
                .andExpect(jsonPath(DATOS + ".idCliente", is(entrada.getIdCliente())))
                .andExpect(jsonPath(DATOS + ".ent_fecha_str", is(entrada.getEnt_fecha_str())))
                .andExpect(jsonPath(DATOS + ".ent_fila", is(entrada.getEnt_fila())))
                .andExpect(jsonPath(DATOS + ".ent_numero", is(entrada.getEnt_numero())))
                ;
    }

    @Test
    void testEliminar() throws Exception {
        Long id = 1L;
        when(cDao.leerUno(any(Long.class))).thenReturn(Optional.of(entrada));
        when(cDao.borrarPorId(any(Long.class))).thenReturn(true);

        mockMvc.perform(delete(RUTAb + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(STATUS, is(1)))
                .andExpect(jsonPath(MENSAJE, is(Constantes.MSJ_ELIMINACION_OK)));
    }
}