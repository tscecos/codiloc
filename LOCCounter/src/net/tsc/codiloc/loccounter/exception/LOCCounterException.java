package net.tsc.codiloc.loccounter.exception;

/**
 * Representa un error en la obtención de LOC.
 * 
 * @author Diego Alejandro Guzmán Trujillo.
 * @version 1.0
 */
public class LOCCounterException extends Exception {

	/**
	 * serialVersionUID - Identificador único de clase.
	 */
	private static final long serialVersionUID = 1057847789118897813L;

	/**
	 * Inicializa un error en la obtención de LOC.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public LOCCounterException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en la obtención de LOC
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
	 * Inicializa un error en la obtención de LOC
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public LOCCounterException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en la obtención de LOC
	 */
	public LOCCounterException() {
		super();
	}
}
