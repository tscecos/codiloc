package net.tsc.codiloc.loccounter.utils;


/**
 * Utiler�a para el manejo de LOC(Line Of Code) para el c�digo fuente de
 * programas.
 * 
 * @author Diego Alejandro Guzm�n Trujillo.
 * @version 1.0
 */
public class LOCUtils {

	/**
	 * EOL - Fin de l�nea.
	 */
	private static final String EOL = "\n";

	/**
	 * Las utiler�as no deber�an instanciarse.
	 */
	public LOCUtils() {

	}

	/**
	 * Realiza el conteo f�sico de las l�neas de c�digo fuente.
	 * 
	 * @param sourceCode
	 *            C�digo fuente.
	 * @return L�neas de c�digo fuente f�sico.
	 * @throws IllegalArgumentException
	 *             Si <code>sourceCode</code> es <code>null</code>.
	 */
	public static int countSourceCodeLOC(String sourceCode) {
		if (sourceCode == null)
			throw new IllegalArgumentException("sourceCode must not be null");
		String[] sourceCodeLines = sourceCode.split(EOL);
		int loc = sourceCodeLines.length;
		sourceCodeLines = null;
		return loc;
	}

	/**
	 * Realiza el conteo f�sico de las l�neas que son comentarios o espacios en
	 * blanco.
	 * 
	 * @param sourceCode
	 *            C�digo fuente.
	 * @return L�neas de c�digo fuente f�sico.
	 * @throws IllegalArgumentException
	 *             Si <code>sourceCode</code> es <code>null</code>.
	 */
	public static int countCommentsAndBlankSpaces(String sourceCode) {
		if (sourceCode == null)
			throw new IllegalArgumentException("sourceCode must not be null");
		int counterLines = 0;
		String[] sourceCodeLines = sourceCode.split(EOL);
		for (String sourceCodeLine : sourceCodeLines) {
			String trimmedSourceCodeLine = sourceCodeLine.trim();
			if (trimmedSourceCodeLine.startsWith("/*")
					|| trimmedSourceCodeLine.startsWith("*")
					|| trimmedSourceCodeLine.startsWith("*/")
					|| trimmedSourceCodeLine.startsWith("//")
					|| trimmedSourceCodeLine.isEmpty())
				counterLines++;
		}
		return counterLines;
	}
}
