package com.recursosformacion.lcs.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RutinasTest_opt {

//	String STRING_NULA;
//	final String STRING_VACIA = "";
//	final String STRING_CON_DATOS = "Hola";
	
//*******Pruebas de validacion de Correo Electronico**************
	final String CORREO_ELECTRONICO_CORRECTO = "migarcia@recursosformacion.com";
	final String CORREO_ELECTRONICO_CORRECTO_1 = "miguel.garcia@recursosformacion.com";
	final String CORREO_ELECTRONICO_CORRECTO_2 = "m@rf.es";
	final String CORREO_ELECTRONICO_ERRONEO_SIN_A = "migarcia.recursosformacion.com";
	final String CORREO_ELECTRONICO_ERRONEO_SIN_TLD = "migarcia@recursosformacion";
	final String CORREO_ELECTRONICO_ERRONEO_SIN_NOMBRE = "@recursosformacion.com";
	final String CORREO_ELECTRONICO_ERRONEO_SIN_DOMINIO = "migarcia@";
	final String CORREO_ELECTRONICO_ERRONEO_CON_NUM = "123@123.12";
	
//********Validacion del DNI**************
	final String NUMERO_DNI_OK = "12.345.678-Z";
	final String NUMERO_DNI_ERROR_LETRA = "12.345.678-Ñ";
	final String NUMERO_DNI_ERROR_FORM_CORTO = "12.2.678-A";
	final String NUMERO_DNI_ERROR_FORM_CORTO2 = "122.678-A";
	final String NUMERO_DNI_ERROR_FORM_LARGO = "123.456.678-A";
	final String NUMERO_DNI_ERROR_FORM_ERR = "12345678A";
	final String NUMERO_DNI_ERROR_FORM_ERR2 = "12.345.678.A";

//**********Validaciones de fechas**********
	final static LocalDate AHORA = LocalDate.now();
	final static LocalDate MANIANA = LocalDate.now().plusDays(1);;
	final static LocalDate AYER = LocalDate.now().minusDays(2);

	final String FECHA_OK = "01/07/2022";
	final String FECHA_OK_1 = "26/12/9999";
	final String FECHA_OK_2 = "08/01/2022";
	final String FECHA_OK_3 = "09/05/2021";

	final String FECHA_ERROR = "2/30/2022";
	final String FECHA_ERROR_1 = "2022/2/22";
	final String FECHA_ERROR_2 = "31/2/2022";
	final String FECHA_ERROR_3 = "2/9993/9";

//***********Validaciones de contraseñas************
	final String CONTRASENIA_OK = "qasLOASD#~@1!!!";
	final String CONTRASENIA_ERROR = "12345";
	final String CONTRASENIA_ERROR_1 = "123456789123456789123456789";
	final String CONTRASENIA_ERROR_2 = "}}¬¬¬||SD";

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "\t", "\n" })
	void testIsVacioLlegaNuloVacio(String entrada) {
		if (entrada == null)
			assertTrue(Rutinas.isVacio(entrada));
		else
			assertTrue(Rutinas.isVacio(entrada.trim()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Hola\t", "h\n","Esto es una cadena con datos tan largos como me parezca" })
	void testIsVacioLleganDatos(String entrada) {
			assertFalse(Rutinas.isVacio(entrada.trim()));
	}

//	@ParameterizedTest
//	@EmptySource
//	void testIsVacioLlegaVacio(String entrada) {
//		assertTrue(Rutinas.isVacio(entrada));      
//    }
//	
//	@ParameterizedTest
//	@NullSource
//	void testIsVacioLlegaDato(String entrada) {
//		assertTrue(Rutinas.isVacio(entrada));      
//    }

	@ParameterizedTest
	@ValueSource(strings = {CORREO_ELECTRONICO_CORRECTO,
			CORREO_ELECTRONICO_CORRECTO_1, 
			CORREO_ELECTRONICO_CORRECTO_2
			}) 
	void testIsEmailValidoOK(String entrada) {
		assertTrue(Rutinas.isEmailValido(entrada));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {CORREO_ELECTRONICO_ERRONEO_SIN_A,
			CORREO_ELECTRONICO_ERRONEO_SIN_TLD, 
			CORREO_ELECTRONICO_ERRONEO_SIN_NOMBRE,
			CORREO_ELECTRONICO_ERRONEO_SIN_DOMINIO,
            CORREO_ELECTRONICO_ERRONEO_CON_NUM
			}) 
	void testIsEmailValidoNOK(String entrada) {
		assertFalse(Rutinas.isEmailValido(entrada));
	}


	@ParameterizedTest
	@ValueSource(strings = {
		NUMERO_DNI_OK})
	void testCumpleDNI (String entrada) {
		assertTrue(Rutinas.cumpleDNI(entrada));
	}
	@ParameterizedTest
	@ValueSource(strings = {
		NUMERO_DNI_ERROR_FORM_CORTO,
		NUMERO_DNI_ERROR_FORM_CORTO2,
		NUMERO_DNI_ERROR_FORM_ERR,
		NUMERO_DNI_ERROR_FORM_ERR2,
		NUMERO_DNI_ERROR_FORM_LARGO,
		NUMERO_DNI_ERROR_LETRA})
	void testNoCumpleDNI (String entrada) {
		assertFalse(Rutinas.cumpleDNI(entrada));
	}

	@Test
	void testNuevoSiNoVacioStringString() {
		String a = null;
		String b = "";
		String c = "Hola";
		String res = "original";

		assertAll(() -> assertEquals(Rutinas.nuevoSiNoVacio(res, a), "original"),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, b), "original"),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, c), "Hola"));
	}

	@Test
	void testNuevoSiNoVacioIntInt() {
		Integer a = null;
		Integer c = 10;
		Integer res = 7;

		assertAll(() -> assertEquals(Rutinas.nuevoSiNoVacio(res, a), 7),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, c), 10));
	}

	@Test
	void testNuevoSiNoVacioLocalDateLocalDate() {
		LocalDate a = null;
		LocalDate c = AHORA;
		LocalDate res = MANIANA;

		assertAll(() -> assertEquals(Rutinas.nuevoSiNoVacio(res, a), MANIANA),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, c), AHORA));

	}

	@Test
	void testNuevoSiNoVacioLongLong() {
		Long a = null;
		Long c = 10L;
		Long res = 7L;

		assertAll(() -> assertEquals(Rutinas.nuevoSiNoVacio(res, a), 7L),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, c), 10L));
	}

	@ParameterizedTest
	@CsvSource (value = {
			"10,,10",
			"7,25,25"
	})
	void testNuevoSiNoVacioLongLong1(Long tengo,Long llega,Long resultado) {
		assertEquals(Rutinas.nuevoSiNoVacio(tengo, llega), resultado);
	}

	@Test
	void testNuevoSiNoVacioObjectObject() {
		Object a = null;
		Object b = "";
		Object c = "Hola";
		Object res = "original";

		assertAll(() -> assertEquals(Rutinas.nuevoSiNoVacio(res, a), "original"),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, b), ""),
				() -> assertEquals(Rutinas.nuevoSiNoVacio(res, c), "Hola"));
	}

	@Test
	void testIsEmptyOrNull() {
		Collection<String> colls = null;
		List<String> listaS = null;
		List<String> listasCreada = new ArrayList<String>();
		List<Integer> listaNum = Arrays.asList(1, 2, 3);

		assertAll(() -> assertTrue(Rutinas.isEmptyOrNull(colls)), () -> assertTrue(Rutinas.isEmptyOrNull(listaS)),
				() -> assertTrue(Rutinas.isEmptyOrNull(listasCreada)),
				() -> assertFalse(Rutinas.isEmptyOrNull(listaNum)));

	}

	
	private static Stream<? extends Arguments> testDeFechas() {
	    return Stream.of(
	    		 Arguments.of(List.of(AHORA,AHORA,0)),
	    		 Arguments.of(List.of(AHORA,MANIANA,-1)),
	    		 Arguments.of(List.of(MANIANA,AHORA,1)),
	    		 Arguments.of(List.of(MANIANA,AYER,3))
	            );
	}
	@ParameterizedTest
	@MethodSource("testDeFechas")
	void testComparaFechas2(List variables) {
		 LocalDate fecha = (LocalDate)variables.get(0);
		 LocalDate referencia = (LocalDate)variables.get(1);
		 assertEquals(Rutinas.comparaFechas(fecha,referencia), variables.get(2));
		}

	@Test
	void testIsGreater() {
		assertAll(() -> assertTrue(Rutinas.isGreaterOrEqual(AHORA, AHORA)),
				() -> assertTrue(Rutinas.isGreater(MANIANA, AHORA)), 
				() -> assertTrue(Rutinas.isGreater(AHORA, AYER)),
				() -> assertTrue(Rutinas.isGreater(MANIANA, AYER)),

				() -> assertFalse(Rutinas.isGreater(AHORA, MANIANA)), 
				() -> assertFalse(Rutinas.isGreater(AYER, AHORA)),
				() -> assertFalse(Rutinas.isGreater(AYER, MANIANA)));
	}

	@Test
	void testIsLess() {
		assertAll(() -> assertTrue(Rutinas.isLessOrEqual(AHORA, AHORA)),
				() -> assertTrue(Rutinas.isLess(AYER, MANIANA)), 
				() -> assertTrue(Rutinas.isLess(AHORA, MANIANA)),
				() -> assertTrue(Rutinas.isLess(AYER, AHORA)),

				() -> assertFalse(Rutinas.isLess(MANIANA, AHORA)), 
				() -> assertFalse(Rutinas.isLess(AHORA, AYER)),
				() -> assertFalse(Rutinas.isLess(MANIANA, AYER)));
	}

	@ParameterizedTest
	@ValueSource(strings = {
		FECHA_OK,
		FECHA_OK_1,
		FECHA_OK_2,
		FECHA_OK_3,
		}
			)		
	void testEsFechaValida(String entrada) {
		assertTrue(Rutinas.esFechaValida(entrada));
	}
	@ParameterizedTest
	@ValueSource(strings = {		
		FECHA_ERROR,
		FECHA_ERROR_1,
		FECHA_ERROR_2,
		FECHA_ERROR_3}
			)		
	void testNoEsFechaValida( String entrada) {
		assertFalse(Rutinas.esFechaValida(entrada));
	}

	@ParameterizedTest
	@ValueSource(strings = {	
			CONTRASENIA_OK,
			}
	)
	void testEsPasswordValida(String entrada) {
		assertTrue(Rutinas.esPasswordValida(entrada));
		
	}
	@ParameterizedTest
	@ValueSource(strings = {	
			CONTRASENIA_ERROR,
            CONTRASENIA_ERROR_1,
            CONTRASENIA_ERROR_2}
	)
	void testNoEsPasswordValida(String entrada) {
		assertFalse(Rutinas.esPasswordValida(entrada));
		
	}

}
