package net.tsc.codiloc.filemanager.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.tsc.codiloc.filemanager.exception.FileManagerException;

import org.apache.commons.io.FileUtils;

/**
 * Fachada para el manejo de archivos.
 * 
 * @author Diego Alejandro Guzmán Trujillo.
 * @version 1.0
 */
public class FileManagerFacade {

	/**
	 * instance - Instancia singleton.
	 */
	private static FileManagerFacade instance;

	/**
	 * Inicializa la fachada asegurando el singleton.
	 */
	private FileManagerFacade() {

	}

	/**
	 * Crea una instancia singleton de <code>FileManagerFacade</code>.
	 */
	private static synchronized void createInstance() {
		if (instance == null)
			instance = new FileManagerFacade();
	}

	/**
	 * Obtiene una instancia singleton de <code>FileManagerFacade</code>.
	 * 
	 * @return Instancia singleton de <code>FileManagerFacade</code>.
	 */
	public static FileManagerFacade getInstance() {
		createInstance();
		return instance;
	}

	/**
	 * Lee un archivo y obtiene sus líneas como un listado de
	 * <code>String</code>.
	 * 
	 * @param file
	 *            Archivo a leer
	 * @return Listado de <code>String</code> representando las líneas del
	 *         archivo.
	 * @throws IllegalArgumentException
	 *             Si <code>file</code> es <code>null</code>.
	 * @throws FileManagerException
	 *             Si ocurre un error leyendo y obteniendo las líneas de texto
	 *             de un archivo.
	 */
	public List<String> getLinesFromFile(File file) throws FileManagerException {
		if (file == null)
			throw new IllegalArgumentException("file must not br null");
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(file);
		} catch (IOException e) {
			String msg = "An IOException occurred at FileManagerFacade.getLinesFromFile";
			throw new FileManagerException(msg, e);
		}
		return lines;
	}
}
