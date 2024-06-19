package com.recursosformacion.lcs.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.recursosformacion.lcs.repository.ICine;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Cine;

@DataJpaTest
class CineServiceTest {

	@Autowired
	private ICine cineRepository;

	private CineService cineServicio;

	Cine cine;
	Cine cine20;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		cine = new Cine(1L, "Dorado", "Floridablanca 95", "Ensanche",400,null);
		cine20 = new Cine(20L, "Granvia","Granvia323", "Ensanche",100, null);
		cineServicio = new CineService(cineRepository);
	}

	@Test
	void testInsert() throws DomainException, DAOException {
		Cine ci = cineServicio.insert(cine);

		Cine ciDB = cineServicio.leerUno(ci.getId_cine()).get();
		assertNotNull(ciDB);
		assertEquals(cine.getCi_nombre(), ciDB.getCi_nombre());
		assertEquals(cine.getCi_calle(), ciDB.getCi_calle());

	}

	@Test
	void testUpdate() throws DomainException, DAOException {
		Cine ci = cineServicio.insert(cine);
		ci.setCi_nombre("Comedia");
		cineServicio.update(ci);
		Cine ciDB = cineServicio.leerUno(ci.getId_cine()).get();
		assertNotNull(ciDB);
		assertEquals(ci.getCi_nombre(), ciDB.getCi_nombre());
		assertEquals(ci.getCi_calle(), ciDB.getCi_calle());
	}

	@Test
	void testUpdateException() throws DomainException, DAOException {
		assertThrows(DAOException.class, () -> cineServicio.update(cine));
	}

	@Test
	void testBorrar() throws DomainException, DAOException {
		Cine ci = cineServicio.insert(cine);
		assertTrue(cineServicio.borrar(ci));
		assertFalse(cineServicio.existe(ci.getId_cine()));
	}

	@Test
	void testBorrarException() throws DomainException, DAOException {
		Cine ci = new Cine(999L, "Cine no existe", "La calle" , 200);
		assertThrows(DAOException.class, () -> cineServicio.borrar(ci));
	}

	@Test
	void testBorrarPorId() throws DomainException, DAOException {

		assertTrue(cineServicio.borrarPorId(10L));

		assertFalse(cineServicio.existe(10L));
	}
	
	@Test
	void testBorrarPorIdException() throws DomainException, DAOException {
		assertThrows(DAOException.class, () -> cineServicio.borrarPorId(9999L));
	}

	@Test
	void testListTodos() {
		List<Cine> cines = cineServicio.listarTodos();
		assertNotNull(cines);
		assertEquals(7, cines.size());

	}

	@Test
	void testListTodosException() {
//		List<Cine> cinesB = cineServicio.listarTodos();
//		cinesB.forEach(p -> {
//			try {
//				cineServicio.borrar(p);
//			} catch (DAOException e) {
//			}
//		});
        cineRepository.deleteAll();	
		List<Cine> cines = cineServicio.listarTodos();
		assertNotNull(cines);
		assertEquals(0, cines.size());
	}

	@Test
	void testLeerUno() throws DomainException, DAOException {
		Optional<Cine> ciDBo = cineServicio.leerUno(cineServicio.insert(cine20).getId_cine());
		assertTrue(ciDBo.isPresent());
		Cine ciDB = ciDBo.get();
		
		assertEquals(cine20.getCi_nombre(), ciDB.getCi_nombre());
		assertEquals(cine20.getCi_calle(), ciDB.getCi_calle());
		assertEquals(cine20.getCi_barrio(), ciDB.getCi_barrio());
		assertEquals(cine20.getCi_capacidad(), ciDB.getCi_capacidad());
		
	}

	@Test
	void testLeerUnoException() {
		assertFalse(cineServicio.leerUno(9999l).isPresent());
	}

	@Test
	void testExiste() throws DomainException, DAOException {
		assertTrue(cineServicio.existe(11L));
	}

	@Test
	void testExisteFalso() throws DomainException, DAOException {
		assertFalse(cineServicio.existe(9999L));
	}

	void verTabla(String titulo) {
		System.out.println(titulo+" ----------------------");
		List<Cine> cinesB = cineServicio.listarTodos();
		cinesB.forEach(p -> {
			System.out.println(p);
		});
	}
}
