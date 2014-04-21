package net.tsc.codiloc.loccounter.exception;

/**
 * Representa un error en la obtenci�n de LOC.
 * 
 * @author Diego Alejandro Guzm�n Trujillo.
 * @version 1.0
 */
public class LOCCounterException extends Exception {

	/**
	 * serialVersionUID - Identificador �nico de clase.
	 */
	private static final long serialVersionUID = 1057847789118897813L;

	/**
	 * Inicializa un error en la obtenci�n de LOC.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public LOCCounterException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en la obtenci�n de LOC
	 * 
	 * @param message
	 *            Mensaje.
	 * @param cause
	 *            Causa.
	 */
	public LOCCounterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Inicializa un error en la obtenci�n de LOC
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public LOCCounterException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en la obtenci�n de LOC
	 */
	public LOCCounterException() {
		super();
	}
}
