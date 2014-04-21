package net.tsc.codiloc.loccounter.utils;

/**
 * Utilería para el manejo de texto.
 * 
 * @author Diego Alejandro Guzmán Trujillo.
 * @version 1.0
 */
public class TextUtils {

	/**
	 * Las utilerías no deberían instanciarse.
	 */
	public TextUtils() {

	}

	/**
	 * Determina si un texto es <code>null</code> o vacío.
	 * 
	 * @param txt
	 *            Texto a evaluar.
	 * @return <code>true</code> si es <code>null</code> o vacío,
	 *         <code>false</code> en caso contrario.
	 */
	public static boolean isBlank(String txt) {
		if (txt == null)
			return true;
		return txt.length() <= 0;
	}
}