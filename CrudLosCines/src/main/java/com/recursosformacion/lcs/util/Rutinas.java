package com.recursosformacion.lcs.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class Rutinas {

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	/**
	 * Patron para validar el email, evitando puntos finales antes de la @ y que
	 * solo contenga caracteres alfanumericos
	 */
	private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Permite verificar que un DNI cumple con el Patron XX.XXX.XXX-L
	 */
	private final static String DNI_PATTERN = "\\d{2}\\.\\d{3}\\.\\d{3}-[a-zA-Z]";

	/**
	 * Letras con las cuales se comprobara la validez del DNI
	 */
	private final static String LETRA_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

	/**
	 * Longitud que debe tener todo DNI pasado a la aplicacion.
	 */
	private final static int LONGITUD_DNI = 12;

	/**
	 * *******************************************************************************
	 * Devuelve true si la string pasada es nula o vacia
	 * 
	 * @param email String a comprobar
	 * @return true, en caso que el formato sea valido
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static boolean isVacio(String prueba) {
		return prueba == null || prueba.equalsIgnoreCase("");
	}

	/**
	 * *******************************************************************************
	 * Comprueba si el email tiene un formato que pueda ser valido.
	 * 
	 * @param email String a comprobar
	 * @return true, en caso que el formato sea valido
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 * 
	 **************************************************************************************/
	public static boolean isEmailValido(String email) {
		return !isVacio(email) && email.matches(EMAIL_PATTERN);
	}

	/**
	 * *******************************************************************************
	 * Devuelve el valorNuevo si este no es nullo o vacio, si no, devuelve
	 * valorActual
	 *
	 * @param valorActual Valor que tiene el campo en la actualidad
	 * @param valorNuevo  Valor que llega para modificar el valor actual
	 * @return true, en caso que el formato sea valido
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	static public String nuevoSiNoVacio(String valorActual, String valorNuevo) {
		if (Objects.nonNull(valorNuevo) && !"".equalsIgnoreCase(valorNuevo)) {
			return valorNuevo;
		} else {
			return valorActual;
		}
	}

	static public int nuevoSiNoVacio(int valorActual, int valorNuevo) {
		if (Objects.nonNull(valorNuevo)) {
			return valorNuevo;
		} else {
			return valorActual;
		}
	}

	static public LocalDate nuevoSiNoVacio(LocalDate valorActual, LocalDate valorNuevo) {
		if (Objects.nonNull(valorNuevo)) {
			return valorNuevo;
		} else {
			return valorActual;
		}
	}

	static public Long nuevoSiNoVacio(long valorActual, long valorNuevo) {
		if (Objects.nonNull(valorNuevo)) {
			return valorNuevo;
		} else {
			return valorActual;
		}
	}

	static public Long nuevoSiNoVacio(Long valorActual, Long valorNuevo) {
		if (Objects.nonNull(valorNuevo)) {
			return valorNuevo;
		} else {
			return valorActual;
		}
	}

	static public Object nuevoSiNoVacio(Object valorActual, Object valorNuevo) {
		if (Objects.nonNull(valorNuevo)) {
			return valorNuevo;
		} else {
			return valorActual;
		}
	}

	/**
	 * **********************************************************************************
	 * Devuelve True si la coleccion recibida es nula o vacia
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmptyOrNull(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * ***********************************************************************************
	 * Esta funcion verifica que el DNI cumple el siguiente formato: xx.xxx.xxx-L y
	 * la longitud correcta
	 * 
	 * @param dni String con DNI a ser validado
	 * @return true, si el DNI cumple el estandar nacional.
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 * 
	 **************************************************************************************/
	public static boolean cumpleDNI(String dni) {
		if (dni == null) {
			return false;
		}

		// si es un NIE se hacen las operaciones necesarias para poder calcular luego la
		// letra correcta

		if (dni.startsWith("X")) {
			dni = dni.replaceFirst("X", "0");
		} else if (dni.startsWith("Y")) {
			dni = dni.replaceFirst("Y", "1");
		} else if (dni.startsWith("Z")) {
			dni = dni.replaceFirst("Z", "2");
		}

		if (dni.length() != LONGITUD_DNI) {
			return false;
		}

		if (!dni.matches(DNI_PATTERN)) {
			return false;
		}

		String dniNumerico = dni.substring(0, dni.length() - 2).replace(".", "");
		int valorNumerico = Integer.parseInt(dniNumerico);

		Character letraDNI = Character.toUpperCase(dni.charAt(dni.length() - 1));

		if (LETRA_DNI.charAt(valorNumerico % 23) == letraDNI) {
			return true;
		} else {
			return false;
		}

	}

	/*****************************************************************************************
	 * Fechas
	 *****************************************************************************************/

	/**
	 * *******************************************************************************
	 * Compara dos fechas
	 * 
	 * @param fecha Fecha a comprobar uno
	 * @param min   Fecha comparacion
	 * @return -1, 0 +1
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static int comparaFechas(LocalDate fecha, LocalDate min) {
		if (fecha != null && min != null) {
			return fecha.compareTo(min);
		}
		return 999;
	}

	/**
	 * *******************************************************************************
	 * Valida fecha superior a minima
	 * 
	 * @param fecha Fecha a comprobar uno
	 * @param min   Fecha comparacion
	 * @return True
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static boolean isGreater(LocalDate fecha, LocalDate min) {
		if (Rutinas.comparaFechas(fecha, min) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * *******************************************************************************
	 * Valida fecha superior o igual a minima
	 * 
	 * @param fecha Fecha a comprobar uno
	 * @param min   Fecha comparacion
	 * @return True
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static boolean isGreaterOrEqual(LocalDate fecha, LocalDate min) {
		if (Rutinas.comparaFechas(fecha, min) >= 0 || Rutinas.comparaFechas(fecha, min) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * ****************************************************************************
	 * Valida una fecha inferior a minima
	 * 
	 * @param fecha Fecha a comprobar
	 * @param min   Fecha comparacion
	 * @return True
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static boolean isLess(LocalDate fecha, LocalDate min) {
		if (Rutinas.comparaFechas(fecha, min) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * ****************************************************************************
	 * Valida una fecha inferior o igual a minima
	 * 
	 * @param fecha Fecha a comprobar
	 * @param min   Fecha comparacion
	 * @return True
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static boolean isLessOrEqual(LocalDate fecha, LocalDate min) {
		if (Rutinas.comparaFechas(fecha, min) <= 0 ||Rutinas.comparaFechas(fecha, min) == 0 ) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * *******************************************************************************
	 * esFechaValida Recibe una string con formato fecha dd/mm/aaaa y comprueba el
	 * formato
	 * 
	 * @param fecha
	 * @return
	 * @date: Octubre 2023
	 * @author: Miguel Garcia
	 */
	public static boolean esFechaValida(String fecha) {
		if (isVacio(fecha)) {
			return false;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Optional<LocalDate> date = Optional.empty();
		try {
			date = Optional.of(LocalDate.parse(fecha, formatter));
			if (date.isPresent()) {
				return true;
			}
		} catch (DateTimeParseException e) {
		}
		System.out.println(fecha);
		return false;

	}

	/**
	 * Comprueba que la cadena recibida cumpla con las normas de contraseña
	 * 
	 * @param password string con la contraseña introducida
	 * @return true si cumple con las especificaciones
	 */
	public static boolean esPasswordValida(String password) {
		return !isVacio(password) && password.matches(PASSWORD_PATTERN);

	}

}
