package com.recursosformacion.lcs.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

import com.recursosformacion.lcs.repository.IEntrada;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Entrada;

@DataJpaTest
class EntradaServiceTest {

	@Autowired
	private  IEntrada entradaRepository;
	
	private  EntradaService entradaServicio;

	Entrada entrada;
	Entrada entrada11;

	@BeforeAll	
	static void setUpBeforeClass() throws Exception {
		
	}
	
	@BeforeEach
	void setUp() throws Exception {
		entrada = new Entrada(1l, LocalDate.now(), 1, 10, "123456", 11l);
		entradaServicio = new EntradaService(entradaRepository);
		entrada11 = new Entrada(11l, LocalDate.parse("2024-01-01"), 5, 10, "12345678Z", 10l);
		
	}

	@Test
	void testInsert() {

		entradaServicio.insert(entrada);

		Entrada entradaDB = entradaServicio.leerUno(entrada.getId_entrada()).get();
		assertNotNull(entradaDB);
		assertEquals(entrada.getIdCliente(), entradaDB.getIdCliente());
		assertEquals(entrada.getEntCine(), entradaDB.getEntCine());
		assertEquals(entrada.getEnt_fecha(), entradaDB.getEnt_fecha());
		assertEquals(entrada.getEnt_fila(), entradaDB.getEnt_fila());
		assertEquals(entrada.getEnt_numero(), entradaDB.getEnt_numero());
		assertEquals(entrada.getId_entrada(), entradaDB.getId_entrada());

	}

	@Test
	void testListAll() {

		List<Entrada> entradas = entradaServicio.listAll();
		assertNotNull(entradas);
		assertEquals(entradas.size(), 6);

	}

	@Test
	void testEntradaPorIdCliente() {
		Entrada entradaDB = entradaServicio.findByIdCliente("12345678Z").get(0);
		assertNotNull(entradaDB);
		assertEquals("12345678Z", entradaDB.getIdCliente());
		assertEquals(10, entradaDB.getEntCine());
		assertEquals("01/01/2024", entradaDB.getEnt_fecha_str());
		assertEquals(5, entradaDB.getEnt_fila());
		assertEquals(10, entradaDB.getEnt_numero());
		assertEquals(11, entradaDB.getId_entrada());
	}
	
	@Test
	void testEntradaPorIdCliente_Exception() {
        assertTrue(entradaServicio.findByIdCliente("9999").isEmpty());
	}
	@Test
	void testUpdate() throws DomainException, DAOException {
		entrada.setId_entrada(12l);
		entradaServicio.insert(entrada);
		entrada.setEntCine(2l);
		entradaServicio.update(entrada);

		Entrada entradaDB = entradaServicio.leerUno(entrada.getId_entrada()).get();
		assertNotNull(entradaDB);
		assertEquals(entrada.getIdCliente(), entradaDB.getIdCliente());
		assertEquals(entrada.getEntCine(), entradaDB.getEntCine());
		assertEquals(entrada.getEnt_fecha(), entradaDB.getEnt_fecha());
		assertEquals(entrada.getEnt_fila(), entradaDB.getEnt_fila());
		assertEquals(entrada.getEnt_numero(), entradaDB.getEnt_numero());
		assertEquals(entrada.getId_entrada(), entradaDB.getId_entrada());
	}
	
	@Test
	void testUpdateException() {
		entrada.setId_entrada(9999l);
		assertThrows(DAOException.class, () -> entradaServicio.update(entrada));
	}

	@Test
	void testDeleteById() throws DAOException {
		entradaServicio.deleteById(12l);
		assertFalse(entradaServicio.leerUno(12l).isPresent());
	}
	
	@Test
	void testDeleteByIdException() {
		assertThrows(DAOException.class, () -> entradaServicio.deleteById(9999l));
	}

	@Test
	void testLeerUno() {
		Entrada entradaDB = entradaServicio.leerUno(11L).get();
		assertNotNull(entradaDB);
		assertEquals(entrada11.getIdCliente(), entradaDB.getIdCliente());
		assertEquals(entrada11.getEntCine(), entradaDB.getEntCine());
		assertEquals(entrada11.getEnt_fecha(), entradaDB.getEnt_fecha());
		assertEquals(entrada11.getEnt_fila(), entradaDB.getEnt_fila());
		assertEquals(entrada11.getEnt_numero(), entradaDB.getEnt_numero());
		assertEquals(entrada11.getId_entrada(), entradaDB.getId_entrada());
	}
	
	@Test
	void testLeerUnoException() {
		assertFalse(entradaServicio.leerUno(9999l).isPresent());
	}

	@Test
	void testFindByEntCine() {
		List<Entrada> entradas = entradaServicio.findByEntCine(10l);
		assertNotNull(entradas);
		assertEquals(entradas.size(), 3);

	}
	
	@Test
	void testFindByEntCineException() {
		assertTrue( entradaServicio.findByEntCine(9999l).isEmpty());

	}

	@Test
	void testExistsById() {
		assertTrue(entradaServicio.existsById(14l));
	}
	
	@Test
	void testExistsByIdException() {
		assertFalse(entradaServicio.existsById(9999l));
	}

}
