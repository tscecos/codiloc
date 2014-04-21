package net.tsc.codiloc.loccounter.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Caso de pruebas para la clase <code>LOCUtils</code>.
 * 
 * @author Diego Alejandro Guzm�n Trujillo <da.guzman12@uniandes.edu.co>.
 * @version 1.0
 */
public class LOCUtilsTest {

	/**
	 * private static final String SOURCE_CODE_FRAGMENT - Fragmento de c�digo de
	 * pruebas.
	 */
	private static final String SOURCE_CODE_FRAGMENT = "public void setX(int x) {\nint y = x;\n}";

	/**
	 * IN_LINE_COMMENT - Comentario de una sola l�nea.
	 */
	private static final String IN_LINE_COMMENT = "//In-line comment";

	/**
	 * MULTI_LINE_COMMENT - Comentario multil�nea.
	 */
	private static final String MULTI_LINE_COMMENT = "/*\n*/";

	/**
	 * JAVA_DOC_COMMENT - Comentario Java Doc.
	 */
	private static final String JAVA_DOC_COMMENT = "/**\n*\n*/";

	/**
	 * BLACK_SPACE - Espacio en blanco.
	 */
	private static final String BLACK_SPACE = "";

	/**
	 * Prueba que cuenta las l�neas de c�digo f�sicas de un fragmento de c�digo.
	 */
	@Test
	public final void testCountSourceCodeLOC() {
		Assert.assertTrue(LOCUtils.countSourceCodeLOC(SOURCE_CODE_FRAGMENT) == 3);
	}

	/**
	 * Prueba que cuenta las l�neas de c�digo que son c�digo l�gico, por
	 * ejemplo: comentarios y espacios en blanco.
	 */
	@Test
	public final void testCountCommentsAndBlankSpaces() {
		Assert.assertTrue(LOCUtils.countCommentsAndBlankSpaces(IN_LINE_COMMENT) == 1);
		Assert.assertTrue(LOCUtils
				.countCommentsAndBlankSpaces(MULTI_LINE_COMMENT) == 2);
		Assert.assertTrue(LOCUtils
				.countCommentsAndBlankSpaces(JAVA_DOC_COMMENT) == 3);
		Assert.assertTrue(LOCUtils.countCommentsAndBlankSpaces(BLACK_SPACE) == 1);
	}
}
