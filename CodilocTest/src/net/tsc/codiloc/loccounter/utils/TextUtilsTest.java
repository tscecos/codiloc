package net.tsc.codiloc.loccounter.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Caso de pruebas para la clase <code>TextUtils</code>.
 * 
 * @author Diego Alejandro Guzmán Trujillo <da.guzman12@uniandes.edu.co>.
 * @version 1.0
 */
public class TextUtilsTest {

	/**
	 * NULL_TEST_STRING - Cadena de caracteres de pruebas <code>null</code>.
	 */
	private static String NULL_TEST_STRING = null;

	/**
	 * EMPTY_TEST_STRING - Cadena de caracteres de pruebas vacía.
	 */
	private static String EMPTY_TEST_STRING = "";

	/**
	 * Prueba que un <code>String</code> sea <code>null</code> o vacío.
	 */
	@Test
	public final void testIsBlank() {
		Assert.assertTrue(TextUtils.isBlank(NULL_TEST_STRING));
		Assert.assertTrue(TextUtils.isBlank(EMPTY_TEST_STRING));
	}
}
