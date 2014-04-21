package net.tsc.codiloc.loccounter.model;

import net.tsc.codiloc.loccounter.exception.LOCCounterException;
import net.tsc.codiloc.loccounter.utils.LOCUtils;

/**
 * Fachada para el manejo de LOC(Line Of Code) para el código fuente de
 * programas.
 * 
 * @author Diego Alejandro Guzmán Trujillo.
 * @version 1.0
 */
public class LOCFacade {

	/**
	 * instance - Instancia singleton.
	 */
	private static LOCFacade instance;

	/**
	 * Inicializa la fachada asegurando el singleton.
	 */
	private LOCFacade() {

	}

	/**
	 * Crea una instancia singleton de <code>LOCFacade</code>.
	 */
	private static synchronized void createInstance() {
		if (instance == null)
			instance = new LOCFacade();
	}

	/**
	 * Obtiene una instancia singleton de <code>LOCFacade</code>.
	 * 
	 * @return Instancia singleton de <code>LOCFacade</code>.
	 */
	public static LOCFacade getInstance() {
		createInstance();
		return instance;
	}

	/**
	 * Realiza el conteo de líneas de código efectivas(loc lógico) de un
	 * fragmento de código.
	 * 
	 * @param sourceCode
	 *            Fragmento de código.
	 * @return líneas de código efectivas(loc lógico).
	 * @throws LOCCounterException
	 *             Si ocurre un error en el conteo de líneas de código.
	 */
	public int count(String sourceCode) throws LOCCounterException {
		int loc = LOCUtils.countSourceCodeLOC(sourceCode)
				- LOCUtils.countCommentsAndBlankSpaces(sourceCode);
		return loc;
	}
}
