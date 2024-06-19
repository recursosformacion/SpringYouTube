package com.recursosformacion.lcs.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.recursosformacion.lcs.repository.IPelicula;
import com.recursosformacion.lcs.exception.DAOException;
import com.recursosformacion.lcs.exception.DomainException;
import com.recursosformacion.lcs.persistence.entity.Pelicula;

@DataJpaTest
class PeliculaServiceTest {

	@Autowired
	private IPelicula peliculaRepository;

	private PeliculaService peliculaServicio;

	Pelicula pelicula;
	Pelicula pelicula20;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		pelicula = new Pelicula(1, "La pelicula", 8);
		pelicula20 = new Pelicula(20, "La gran pelicula", 1);
		peliculaServicio = new PeliculaService(peliculaRepository);
	}

	@Test
	void testInsert() throws DomainException, DAOException {
		Pelicula peli = peliculaServicio.insert(pelicula);

		Pelicula peliDB = peliculaServicio.leerUno(peli.getId_pelicula()).get();
		assertNotNull(peliDB);
		assertEquals(pelicula.getPe_titulo(), peliDB.getPe_titulo());
		assertEquals(pelicula.getPe_identificador(), peliDB.getPe_identificador());

	}

	@Test
	void testUpdate() throws DomainException, DAOException {
		Pelicula peli = peliculaServicio.insert(pelicula);
		peli.setPe_titulo("La pelicula actualizada");
		peliculaServicio.update(peli);
		Pelicula peliDB = peliculaServicio.leerUno(peli.getId_pelicula()).get();
		assertNotNull(peliDB);
		assertEquals(peli.getPe_titulo(), peliDB.getPe_titulo());
		assertEquals(peli.getPe_identificador(), peliDB.getPe_identificador());
	}

	@Test
	void testUpdateException() throws DomainException, DAOException {
		assertThrows(DAOException.class, () -> peliculaServicio.update(pelicula));
	}

	@Test
	void testPatch() throws DomainException, DAOException {
		Pelicula peli = peliculaServicio.insert(pelicula);
		peli.setPe_titulo("La pelicula actualizada");
		peli.setPe_identificador(0);
		peliculaServicio.patch(peli);
		Pelicula peliDB = peliculaServicio.leerUno(pelicula.getId_pelicula()).get();
		assertNotNull(peliDB);
		assertEquals(peli.getPe_titulo(), peliDB.getPe_titulo());
		assertEquals(pelicula.getPe_identificador(), peliDB.getPe_identificador());
	}

	@Test
	void testBorrar() throws DomainException, DAOException {
		Pelicula peli = peliculaServicio.insert(pelicula);
		assertTrue(peliculaServicio.borrar(peli));
		assertFalse(peliculaServicio.existe(peli.getId_pelicula()));
	}

	@Test
	void testBorrarException() throws DomainException, DAOException {
		Pelicula peli = new Pelicula(999L, "no existe", 0);
		assertThrows(DAOException.class, () -> peliculaServicio.borrar(peli));
	}

	@Test
	void testBorrarPorId() throws DomainException, DAOException {

		assertTrue(peliculaServicio.borrarPorId(20L));

		assertFalse(peliculaServicio.existe(20L));
	}
	
	@Test
	void testBorrarPorIdException() throws DomainException, DAOException {
		assertThrows(DAOException.class, () -> peliculaServicio.borrarPorId(9999L));
	}

	@Test
	void testListTodos() {
		List<Pelicula> peliculas = peliculaServicio.listarTodos();
		assertNotNull(peliculas);
		assertEquals(3, peliculas.size());

	}

	@Test
	void testListTodosException() {
//		List<Pelicula> peliculasB = peliculaServicio.listarTodos();
//		peliculasB.forEach(p -> {
//			try {
//				peliculaServicio.borrar(p);
//			} catch (DAOException e) {
//			}
//		});
        peliculaRepository.deleteAll();	
		List<Pelicula> peliculas = peliculaServicio.listarTodos();
		assertNotNull(peliculas);
		assertEquals(0, peliculas.size());
	}

	@Test
	void testLeerUno() throws DomainException, DAOException {
		peliculaServicio.insert(pelicula20);
		Pelicula peliDB = peliculaServicio.leerUno(pelicula20.getId_pelicula()).get();
		assertNotNull(peliDB);
		assertEquals(pelicula20.getId_pelicula(), peliDB.getId_pelicula());
		assertEquals(pelicula20.getPe_titulo(), peliDB.getPe_titulo());
		assertEquals(pelicula20.getPe_identificador(), peliDB.getPe_identificador());
	}

	@Test
	void testLeerUnoException() {
		assertFalse(peliculaServicio.leerUno(9999l).isPresent());
	}

	@Test
	void testExiste() throws DomainException, DAOException {
		assertTrue(peliculaServicio.existe(20L));
	}

	@Test
	void testExisteFalso() throws DomainException, DAOException {
		assertFalse(peliculaServicio.existe(9999L));
	}

	void verTabla(String titulo) {
		System.out.println(titulo+" ----------------------");
		List<Pelicula> peliculasB = peliculaServicio.listarTodos();
		peliculasB.forEach(p -> {
			System.out.println(p);
		});
	}
}
