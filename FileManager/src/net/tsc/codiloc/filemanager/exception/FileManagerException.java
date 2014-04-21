package net.tsc.codiloc.filemanager.exception;

/**
 * Representa un error en el manejo de archivos.
 * 
 * @author Diego Alejandro Guzmán Trujillo.
 * @version 1.0
 */
public class FileManagerException extends Exception {

	/**
	 * serialVersionUID - Identificador único de clase.
	 */
	private static final long serialVersionUID = 1057847789118897813L;

	/**
	 * Inicializa un error en el manejo de archivos.
	 * 
	 * @param cause
	 *            Causa.
	 */
	public FileManagerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Inicializa un error en el manejo de archivos
	 * 
	 * @param message
	 *            Mensaje.
	 * @param cause
	 *            Causa.
	 */
	public FileManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Inicializa un error en el manejo de archivos
	 * 
	 * @param message
	 *            Mensaje.
	 */
	public FileManagerException(String message) {
		super(message);
	}

	/**
	 * Inicializa un error en el manejo de archivos
	 */
	public FileManagerException() {
		super();
	}
}
