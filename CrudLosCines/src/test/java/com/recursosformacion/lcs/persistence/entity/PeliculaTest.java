package com.recursosformacion.lcs.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PeliculaTest {

	@Test
	public void testPeliculaGetters() {
		// Crear una instancia de Pelicula
		Pelicula pelicula = new Pelicula(1L, "El Padrino", 123);

		// Prueba los getters
		assertEquals(1L, pelicula.getId_pelicula());
		assertEquals("El Padrino", pelicula.getPe_titulo());
		assertEquals(123, pelicula.getPe_identificador());

	}

	void testPeliculaSetters() {
		Pelicula pelicula = new Pelicula();

		// Prueba los setters
		pelicula.setId_pelicula(2L);
		pelicula.setPe_titulo("Casablanca");
		pelicula.setPe_identificador(456);

		assertEquals(2L, pelicula.getId_pelicula());
		assertEquals("Casablanca", pelicula.getPe_titulo());
		assertEquals(456, pelicula.getPe_identificador());

	}

	void testPeliculaMetodoToString() {
		Pelicula pelicula = new Pelicula(2L, "Casablanca", 456);
		// Prueba el método toString()
		String expectedString = "Pelicula [id_pelicula=2, pe_titulo=Casablanca, pe_identificador=456]";
		assertEquals(expectedString, pelicula.toString());

	}

	void testPeliculaMetodoValidInsert() {

		Pelicula pelicula = new Pelicula(2L, "Casablanca", 456);
		// Prueba los métodos isValidInsert()
		assertEquals(true, pelicula.isValidInsert());

	}

	void testPeliculaMetodoValidUpdate() {

		Pelicula pelicula = new Pelicula(2L, "Casablanca", 456);
		// Prueba los métodos isValidUpdate()
		assertEquals(true, pelicula.isValidUpdate());
	}

}