package net.tsc.codiloc.loccomparator.exception;

/**
 * Representa un error en la comparación de código fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class ComparatorException extends Exception {

	/**
	 * serialVersionUID - Identificador único de clase.
	 */
	private static final long serialVersionUID = -274379734673795471L;

	/**
	 * Inicializa un error en la comparación de código fuente.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public ComparatorException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en la comparación de código fuente.
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
	 * Inicializa un error en la comparación de código fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public ComparatorException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en la comparación de código fuente.
	 */
	public ComparatorException() {
		super();
	}
}
