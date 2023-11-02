package com.recursosformacion.lcs.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

class RutinasTest {

	String STRING_NULA;
	final String STRING_VACIA = "";
	

	final String CORREO_ELECTRONICO_CORRECTO = "migarcia@recursosformacion.com";
	final String CORREO_ELECTRONICO_ERRONEO_1 = "migarcia.recursosformacion.com";
	final String CORREO_ELECTRONICO_ERRONEO_2 = "migarcia@recursosformacion";
	final String CORREO_ELECTRONICO_ERRONEO_3 = "@recursosformacion.com";
	final String CORREO_ELECTRONICO_ERRONEO_4 = "migarcia@";

	final String NUMERO_DNI_OK = "12.345.678-Z";
	final String NUMERO_DNI_ERROR_LETRA = "12.345.678-Ñ";
	final String NUMERO_DNI_ERROR_FORM_CORTO = "12.2.678-A";
	final String NUMERO_DNI_ERROR_FORM_CORTO2 = "122.678-A";
	final String NUMERO_DNI_ERROR_FORM_LARGO = "123.456.678-A";
	final String NUMERO_DNI_ERROR_FORM_ERR = "12345678A";
	final String NUMERO_DNI_ERROR_FORM_ERR2 = "12.345.678.A";

	final int NUMERO_INT = 0;
	final int NUMERO_INT_NEGATIVO = -90000000;
	final int NUMERO_INT_POSITIVO = 800000000;

	final double NUMERO_DOUBLE = 0.0;
	final double NUMERO_DOUBLE_NEGATIVO = -1764.8889;
	final double NUMERO_DOUBLE_POSITIVO = 86594.6442;

	final LocalDate AHORA = LocalDate.now();
	final LocalDate MANIANA = LocalDate.now().plusDays(1);;
	final LocalDate AYER = LocalDate.now().minusDays(2);

	final String FECHA_OK = "01/07/2022";
	final String FECHA_OK_1 = "26/12/9999";
	final String FECHA_OK_2 = "08/01/2022";
	final String FECHA_OK_3 = "09/05/2021";

	final String FECHA_ERROR = "2/30/2022";
	final String FECHA_ERROR_1 = "2022/2/22";
	final String FECHA_ERROR_2 = "31/2/2022";
	final String FECHA_ERROR_3 = "2/9993/9";

	final String CONTRASENIA_OK = "qasLOASD#~@1!!!";
	final String CONTRASENIA_ERROR = "12345";
	final String CONTRASENIA_ERROR_1 = "123456789123456789123456789";
	final String CONTRASENIA_ERROR_2 = "}}¬¬¬||SD";

	@Test
	void testIsVacio() {
		assertAll(() -> assertTrue(Rutinas.isVacio(STRING_NULA)), () -> assertTrue(Rutinas.isVacio(STRING_VACIA)));
	}

	@Test
	void testIsEmailValido() {
		assertAll(() -> assertTrue(Rutinas.isEmailValido(CORREO_ELECTRONICO_CORRECTO)),
				() -> assertFalse(Rutinas.isEmailValido(CORREO_ELECTRONICO_ERRONEO_1)),
				() -> assertFalse(Rutinas.isEmailValido(CORREO_ELECTRONICO_ERRONEO_2)),
				() -> assertFalse(Rutinas.isEmailValido(CORREO_ELECTRONICO_ERRONEO_3)),
				() -> assertFalse(Rutinas.isEmailValido(CORREO_ELECTRONICO_ERRONEO_4)));
	}

	@Test
	void testCumpleDNI() {
		assertAll(() -> assertTrue(Rutinas.cumpleDNI(NUMERO_DNI_OK)),
				() -> assertFalse(Rutinas.cumpleDNI(NUMERO_DNI_ERROR_FORM_CORTO)),
				() -> assertFalse(Rutinas.cumpleDNI(NUMERO_DNI_ERROR_FORM_CORTO2)),
				() -> assertFalse(Rutinas.cumpleDNI(NUMERO_DNI_ERROR_FORM_ERR)),
				() -> assertFalse(Rutinas.cumpleDNI(NUMERO_DNI_ERROR_FORM_ERR2)),
				() -> assertFalse(Rutinas.cumpleDNI(NUMERO_DNI_ERROR_FORM_LARGO)),
				() -> assertFalse(Rutinas.cumpleDNI(NUMERO_DNI_ERROR_LETRA)));

	}

	@Test
	void testNuevoSiNoVacioStringString() {
		String a = null;
		String b ="";
		String c ="Hola";
		String res="original";
			
		assertEquals(Rutinas.nuevoSiNoVacio(res,a), "original");
		assertEquals(Rutinas.nuevoSiNoVacio(res,b), "original");
		assertEquals(Rutinas.nuevoSiNoVacio(res,c), "Hola");
	}

	@Test
	void testNuevoSiNoVacioIntInt() {
		Integer a = null;
		Integer c =10;
		Integer res=7;
			
		assertEquals(Rutinas.nuevoSiNoVacio(res,a), 7);
		assertEquals(Rutinas.nuevoSiNoVacio(res,c), 10);
	}

	@Test
	void testNuevoSiNoVacioLocalDateLocalDate() {
		LocalDate a = null;
		LocalDate c = AHORA;
		LocalDate res = MANIANA;
			
		assertEquals(Rutinas.nuevoSiNoVacio(res,a), MANIANA);
		assertEquals(Rutinas.nuevoSiNoVacio(res,c), AHORA);
	
	}

	@Test
	void testNuevoSiNoVacioLongLong() {
		Long a = null;
		Long c =10L;
		Long res=7L;
			
		assertEquals(Rutinas.nuevoSiNoVacio(res,a), 7L);
		assertEquals(Rutinas.nuevoSiNoVacio(res,c), 10L);
	}

	@Test
	void testNuevoSiNoVacioLongLong1() {
		long a = 0L;
		long c =10L;
		long res=7L;
			
		assertEquals(Rutinas.nuevoSiNoVacio(res,a), 0L);
		assertEquals(Rutinas.nuevoSiNoVacio(res,c), 10L);
	}

	@Test
	void testNuevoSiNoVacioObjectObject() {
		Object a = null;
		Object b ="";
		Object c ="Hola";
		Object res="original";
			
		assertEquals(Rutinas.nuevoSiNoVacio(res,a), "original");
		assertEquals(Rutinas.nuevoSiNoVacio(res,b), "");
		assertEquals(Rutinas.nuevoSiNoVacio(res,c), "Hola");
	}

	@Test
	void testIsEmptyOrNull() {
		Collection<String> colls = null;
		List<String> listaS = null;
		List<String> listasCreada=new ArrayList<String>();
		List<Integer> listaNum=Arrays.asList(1, 2, 3);
		
		assertAll(() -> assertTrue(Rutinas.isEmptyOrNull(colls)),
				() -> assertTrue(Rutinas.isEmptyOrNull(listaS)), 
				() -> assertTrue(Rutinas.isEmptyOrNull(listasCreada)),
				
				() -> assertFalse(Rutinas.isEmptyOrNull(listaNum)));
				
				
	}

	@Test
	void testComparaFechas() {
		assertAll(() -> assertEquals(Rutinas.comparaFechas(AHORA, AHORA),0),
				() -> assertEquals(Rutinas.comparaFechas(MANIANA, AHORA),1), 
				() -> assertEquals(Rutinas.comparaFechas(AHORA, AYER),1),
				() -> assertEquals(Rutinas.comparaFechas(AYER,MANIANA ),-1));
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

	@Test
	void testEsFechaValida() {
		assertAll(() -> assertTrue(Rutinas.esFechaValida(FECHA_OK)),
				() -> assertTrue(Rutinas.esFechaValida(FECHA_OK_1)),
				() -> assertTrue(Rutinas.esFechaValida(FECHA_OK_2)),
				() -> assertTrue(Rutinas.esFechaValida(FECHA_OK_3)),
				() -> assertFalse(Rutinas.esFechaValida(FECHA_ERROR)),
				() -> assertFalse(Rutinas.esFechaValida(FECHA_ERROR_1)),
				() -> assertFalse(Rutinas.esFechaValida(FECHA_ERROR_2)),
				() -> assertFalse(Rutinas.esFechaValida(FECHA_ERROR_3)));
	}

	@Test
	void testEsPasswordValida() {
		assertAll(() -> assertTrue(Rutinas.esPasswordValida(CONTRASENIA_OK)),
				() -> assertFalse(Rutinas.esPasswordValida(CONTRASENIA_ERROR)),
				() -> assertFalse(Rutinas.esPasswordValida(CONTRASENIA_ERROR_1)),
				() -> assertFalse(Rutinas.esPasswordValida(CONTRASENIA_ERROR_2)));
	}

}
