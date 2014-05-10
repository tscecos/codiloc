package net.tsc.codiloc.versionmanager.exception;

/**
 * Representa un error en el manejo de parametros pasados por l�nea de c�digo.
 * 
 * @author Miguel Arturo Cruz Tovar <ma.cruz14@uniandes.edu.co>
 * @version 1.0
 */
public class WrongParamException extends Exception {

	/**
	 * serialVersionUID - Identificador �nico de clase.
	 */
	private static final long serialVersionUID = -374379734673795471L;

	/**
	 * Inicializa un error en el manejo de versiones de c�digo fuente.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public WrongParamException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en el manejo de versiones de c�digo fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 * @param cause
	 *            Causa.
	 */
	public WrongParamException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Inicializa un error en el manejo de versiones de c�digo fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public WrongParamException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en el manejo de versiones de c�digo fuente.
	 */
	public WrongParamException() {
		super();
	}
}