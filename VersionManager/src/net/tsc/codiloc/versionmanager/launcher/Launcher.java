package net.tsc.codiloc.versionmanager.launcher;

import java.util.logging.Logger;

import net.tsc.codiloc.loccomparator.model.ComparatorFacade;
import net.tsc.codiloc.versionmanager.exception.VersionManagerException;
import net.tsc.codiloc.versionmanager.model.VersionManagerFacade;

/**
 * Clase de inicio del sistema.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class Launcher {

	/**
	 * logger - Bitácora
	 */
	private static Logger logger = Logger.getLogger(ComparatorFacade.class
			.getName());

	/**
	 * Método de inicio del programa
	 * 
	 * @param args
	 *            Rutas de los archivos original y modificado que se van a
	 *            comparar.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			logger.severe("Las rutas de los archivos original y modificado son obligatorias.");
			System.exit(-1);
		}

		VersionManagerFacade versionManager = VersionManagerFacade
				.getInstance();
		try {
			versionManager.compareVersions(args[0], args[1]);
			versionManager.print();
		} catch (VersionManagerException e) {
			logger.severe(e.getMessage());
			System.exit(-1);
		}
		System.exit(0);
	}
}
