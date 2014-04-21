package net.tsc.codiloc.loccounter.utils;

/**
 * Utiler�a para el manejo de texto.
 * 
 * @author Diego Alejandro Guzm�n Trujillo.
 * @version 1.0
 */
public class TextUtils {

	/**
	 * Las utiler�as no deber�an instanciarse.
	 */
	public TextUtils() {

	}

	/**
	 * Determina si un texto es <code>null</code> o vac�o.
	 * 
	 * @param txt
	 *            Texto a evaluar.
	 * @return <code>true</code> si es <code>null</code> o vac�o,
	 *         <code>false</code> en caso contrario.
	 */
	public static boolean isBlank(String txt) {
		if (txt == null)
			return true;
		return txt.length() <= 0;
	}
}