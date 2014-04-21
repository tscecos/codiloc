package net.tsc.codiloc.loccounter.utils;


/**
 * Utilería para el manejo de LOC(Line Of Code) para el código fuente de
 * programas.
 * 
 * @author Diego Alejandro Guzmán Trujillo.
 * @version 1.0
 */
public class LOCUtils {

	/**
	 * EOL - Fin de línea.
	 */
	private static final String EOL = "\n";

	/**
	 * Las utilerías no deberían instanciarse.
	 */
	public LOCUtils() {

	}

	/**
	 * Realiza el conteo físico de las líneas de código fuente.
	 * 
	 * @param sourceCode
	 *            Código fuente.
	 * @return Líneas de código fuente físico.
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
	 * Realiza el conteo físico de las líneas que son comentarios o espacios en
	 * blanco.
	 * 
	 * @param sourceCode
	 *            Código fuente.
	 * @return Líneas de código fuente físico.
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
