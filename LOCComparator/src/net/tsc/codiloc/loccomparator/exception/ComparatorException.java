package net.tsc.codiloc.loccomparator.exception;

/**
 * Representa un error en la comparaci�n de c�digo fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class ComparatorException extends Exception {

	/**
	 * serialVersionUID - Identificador �nico de clase.
	 */
	private static final long serialVersionUID = -274379734673795471L;

	/**
	 * Inicializa un error en la comparaci�n de c�digo fuente.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public ComparatorException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en la comparaci�n de c�digo fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 * @param cause
	 *            Causa.
	 */
	public ComparatorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Inicializa un error en la comparaci�n de c�digo fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public ComparatorException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en la comparaci�n de c�digo fuente.
	 */
	public ComparatorException() {
		super();
	}
}
