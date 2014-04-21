package net.tsc.codiloc.versionmanager.exception;

/**
 * Representa un error en el manejo de versiones de código fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class VersionManagerException extends Exception {

	/**
	 * serialVersionUID - Identificador único de clase.
	 */
	private static final long serialVersionUID = -274379734673795471L;

	/**
	 * Inicializa un error en el manejo de versiones de código fuente.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public VersionManagerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en el manejo de versiones de código fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 * @param cause
	 *            Causa.
	 */
	public VersionManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Inicializa un error en el manejo de versiones de código fuente.
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public VersionManagerException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en el manejo de versiones de código fuente.
	 */
	public VersionManagerException() {
		super();
	}
}