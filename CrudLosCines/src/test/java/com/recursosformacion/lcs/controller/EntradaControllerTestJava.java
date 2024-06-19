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
import org.springframework.boot.test.mock.mockito.MockBean;
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

	private EntradaController entradaController;

	final LocalDate AHORA = LocalDate.now();
	final LocalDate MANIANA = LocalDate.now().plusDays(1);;
	final LocalDate AYER = LocalDate.now().minusDays(1);

	Entrada entrada1;
	Entrada entrada2;
	EntradaDTO entradaDTO;

	@BeforeEach
	void setup() {
		entradaController = new EntradaController(cDao, cineService);

		entrada1 = new Entrada(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A", 10L);
		entradaDTO = new EntradaDTO(1L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A" , 10L);
		entrada2 = new Entrada(2L, MANIANA.format(Constantes.FORMATO_FECHA_EU), 10, 20, "56.789.012-A", 10L);
	}

	@Test
	void testLeerUno() throws ControllerException {
		// Preparar

		when(cDao.leerUno(any(Long.class))).thenReturn(Optional.of(entrada1));

		// Ejecutar
		ResponseEntity<Map<String, Object>> response = entradaController.leerUno(entrada1.getId_entrada());

		// Verificar
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().get(Constantes.STATUS));
		assertEquals(entrada1, response.getBody().get(Constantes.DATOS));
	}

	@Test
	public void testLeerTodos() throws ControllerException {
		// Preparar

		List<Entrada> entradas = Arrays.asList(entrada1, entrada2);
		when(cDao.listarTodos()).thenReturn(entradas);

		// Ejecutar
		ResponseEntity<Map<String, Object>> response = entradaController.leerTodos();

		// Verificar
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().get(Constantes.STATUS));
		assertEquals(entradas, response.getBody().get(Constantes.DATOS));
	}

	@Test
	public void testLeerPorIdCliente() throws ControllerException {
		// Preparar
		String id = "56.789.012-A";
		List<Entrada> entradas = Arrays.asList(entrada1, entrada2);
		when(cDao.buscarPorIdCliente(any(String.class))).thenReturn(entradas);

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
		Long id = 10L;
		List<Entrada> entradas = Arrays.asList(entrada1, entrada2);
		when(cDao.buscarPorEntCine(id)).thenReturn(entradas);

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

		Entrada entrada = entradaController.Dto2Entrada(entradaDTO);
		when(cDao.insert(any(Entrada.class))).thenReturn(entrada);

		// Ejecutar
		ResponseEntity<Map<String, Object>> response = entradaController.alta(entradaDTO);

		// Verificar
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(1, response.getBody().get(Constantes.STATUS));
		assertEquals(entrada, response.getBody().get(Constantes.DATOS));
	}

	@Test
	public void testModificacion() throws ControllerException, DomainException, DAOException {
		// Preparar
		// when(cDao.existe(any(Long.class))).thenReturn(true);
		when(cDao.update(any(Entrada.class))).thenReturn(entrada1);

		// Ejecutar
		ResponseEntity<Map<String, Object>> response = entradaController.modificacion(entradaDTO);

		// Verificar
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().get(Constantes.STATUS));
		assertEquals(entradaDTO.toString().compareTo((response.getBody().get(Constantes.DATOS).toString())),0);
	}

	@Test
	public void testConvertirDTO() throws ControllerException {
		// Preparar

		// Ejecutar
		Entrada entrada = entradaController.Dto2Entrada(entradaDTO);

		// Verificar
		assertEquals(entradaDTO.getId_entrada(), entrada.getId_entrada());
		assertEquals(entradaDTO.getEnt_fila(), entrada.getEnt_fila());
		assertEquals(entradaDTO.getEnt_numero(), entrada.getEnt_numero());
		assertEquals(entradaDTO.getEnt_fecha_str(), entrada.getEnt_fecha_str());
		assertEquals(entradaDTO.getIdCliente(), entrada.getIdCliente());
		assertEquals(entradaDTO.getEntCine(), entrada.getEntCine());
	}
}