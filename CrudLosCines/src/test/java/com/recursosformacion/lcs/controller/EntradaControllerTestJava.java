package com.recursosformacion.lcs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.recursosformacion.lcs.exception.ControllerException;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.model.dto.EntradaDTO;
import com.recursosformacion.lcs.persistence.entity.Entrada;
import com.recursosformacion.lcs.service.CineService;
import com.recursosformacion.lcs.service.EntradaService;
import com.recursosformacion.lcs.util.Constantes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;




@ExtendWith(MockitoExtension.class)
class EntradaControllerTestJava {

    @Mock
    private EntradaService cDao;

    @Mock
    private CineService cineService;

    @Autowired
    private EntradaController entradaController;
    
    final LocalDate AHORA = LocalDate.now();
   	final LocalDate MANIANA = LocalDate.now().plusDays(1);;
   	final LocalDate AYER = LocalDate.now().minusDays(1);

   	@BeforeEach
	void setup() {
   		        entradaController = new EntradaController(cDao, cineService);
   		    }
    @Test
    void testLeerUno() throws ControllerException {
        // Preparar
        Long id = 1L;
        Entrada entrada = new Entrada();
        entrada.setId_entrada(id);
        when(cDao.leerUno(id)).thenReturn(Optional.of(entrada));

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.leerUno(id.toString());

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals(entrada, response.getBody().get(Constantes.DATOS));
    }

    @Test
    public void testLeerTodos() throws ControllerException {
        // Preparar
        Entrada entrada1 = new Entrada();
        entrada1.setId_entrada(1L);
        Entrada entrada2 = new Entrada();
        entrada2.setId_entrada(2L);
        List<Entrada> entradas = Arrays.asList(entrada1, entrada2);
        when(cDao.listAll()).thenReturn(entradas);

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.leerTodos();

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals(entradas, response.getBody().get(Constantes.DATOS));
    }

    @Test
    public void testLeerPorId() throws ControllerException {
        // Preparar
        String id = "1";
        Entrada entrada1 = new Entrada();
        entrada1.setId_entrada(1L);
        Entrada entrada2 = new Entrada();
        entrada2.setId_entrada(2L);
        List<Entrada> entradas = Arrays.asList(entrada1, entrada2);
        when(cDao.findByIdCliente(id)).thenReturn(entradas);

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.leerPorId(id);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals(entradas, response.getBody().get(Constantes.DATOS));
    }

    @Test
    public void testLeerporcine() throws ControllerException {
        // Preparar
        Long id = 1L;
        Entrada entrada1 = new Entrada();
        entrada1.setId_entrada(1L);
        Entrada entrada2 = new Entrada();
        entrada2.setId_entrada(2L);
        List<Entrada> entradas = Arrays.asList(entrada1, entrada2);
        when(cDao.findByEntCine(id)).thenReturn(entradas);

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.leerporcine(id);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals(entradas, response.getBody().get(Constantes.DATOS));
    }
    
    @Test
    public void testAlta() throws ControllerException, DomainException, DAOException {
        // Preparar
    	EntradaDTO entradaDTO = new EntradaDTO(0L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 10L);
        Entrada entrada = entradaController.convertirDTO(entradaDTO);
        when(cDao.insert(any(Entrada.class))).thenReturn(entrada);

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.alta(entradaDTO);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals(entrada, response.getBody().get(Constantes.DATOS));
    }

    @Test
    public void testModificacion() throws ControllerException, DomainException, DAOException {
        // Preparar
    	EntradaDTO entradaDTO = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 10L);
        when(cDao.update(any(Entrada.class))).thenReturn(true);

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.modificacion(entradaDTO);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals("Actualizacion realizada", response.getBody().get(Constantes.MENSAJE));
    }

    // chat copilot
    
    
    
    @Test
    public void testRaror() throws ControllerException {
        // Preparar
        String id = "1";
        Entrada entrada = new Entrada();
        entrada.setId_entrada(1L);
        when(cDao.leerUno(Long.parseLong(id))).thenReturn(Optional.of(entrada));

        // Ejecutar
        ResponseEntity<Map<String, Object>> response = entradaController.eliminar(id);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get(Constantes.STATUS));
        assertEquals("Registro borrado", response.getBody().get(Constantes.MENSAJE));
    }

    @Test
    public void testConvertirDTO() throws ControllerException {
        // Preparar
    	EntradaDTO entradaDTO = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 10L);

        // Ejecutar
        Entrada entrada = entradaController.convertirDTO(entradaDTO);

        // Verificar
        assertEquals(entradaDTO.getId_entrada(), entrada.getId_entrada());
        assertEquals(entradaDTO.getEnt_fila(), entrada.getEnt_fila());
        assertEquals(entradaDTO.getEnt_numero(), entrada.getEnt_numero());
        assertEquals(entradaDTO.getEnt_fecha_str(), entrada.getEnt_fecha_str());
        assertEquals(entradaDTO.getIdCliente(), entrada.getIdCliente());
        assertEquals(entradaDTO.getEntCine(), entrada.getEntCine());
    }
}