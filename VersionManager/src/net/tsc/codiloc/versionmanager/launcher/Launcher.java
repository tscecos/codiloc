package net.tsc.codiloc.versionmanager.launcher;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

//import com.sun.tracing.dtrace.ArgsAttributes;

import net.tsc.codiloc.filemanager.exception.FileManagerException;
import net.tsc.codiloc.filemanager.model.FileManagerFacade;
import net.tsc.codiloc.loccomparator.model.ComparatorFacade;
import net.tsc.codiloc.versionmanager.exception.VersionManagerException;
import net.tsc.codiloc.versionmanager.exception.WrongParamException;
import net.tsc.codiloc.versionmanager.model.VersionManagerFacade;

/**
 * Clase de inicio del sistema.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co> 
 * [20140509]Miguel Cruz<ma.cruz14@uniandes.edu.co>: Se agregan y modifican los métodos
 *         validateParams, showHelp, showResult, ValidateFile, identifiesArguments
 * @version 1.0
 */
public class Launcher {

	/**
	 * logger - Bitácora
	 */
	private static Logger logger = Logger.getLogger(ComparatorFacade.class
			.getName());

	// private static String rutaOriginal = "";
	private static String modifiedFilePath = "";
	private static String user = "";
	private static String comment = "";
	private static final String PERMITTED_EXTENSION = ".java";

	/**
	 * Método de inicio del programa
	 * 
	 * @param args
	 *            Rutas de los archivos original y modificado que se van a
	 *            comparar.
	 * @throws WrongParamException
	 * @throws FileManagerException
	 */
	public static void main(String[] args) throws WrongParamException,
			FileManagerException {

		// showHelp();
		validateParams(args);

		VersionManagerFacade versionManager = VersionManagerFacade
				.getInstance();
		try {
			// rutaOriginal = "D:\\testFiles\\original\\ComparedLine.java";
			// versionManager.compareVersions(rutaOriginal, rutaModificado);
			versionManager.compare(modifiedFilePath, user, comment);
			
			versionManager.showResult();
		} catch (VersionManagerException e) {
			logger.severe(e.getMessage());
			System.exit(-1);
		}
		System.exit(0);
	}

	/**
	 * 
	 * @param args
	 *            Contiene los parámetros pasados por línea de comando
	 *            (([compare][show])[-u][-c][-m][-h][drive:][help])
	 * @throws FileManagerException
	 *             Presenta una excepción cuando ocurre un error con el archivo
	 *             suministrado para comparación
	 * @throws WrongParamException
	 *             Presenta una excepción cuando ocurre un error con los
	 *             parametros suministrados por línea de comando
	 * 
	 */
	private static void validateParams(String[] args)
			throws WrongParamException, FileManagerException {

		int errors = 0;

		if (args.length < 1) {
			errors++; // detecta un error e incrementa el contador para que al
						// final se identifique cuantos errores se presentaron.
		} else {
			if (args[0].compareTo("compare") == 0) {
				for (int i = 1; i < args.length; i += 1) {
					if (args[i].compareTo("-u") != 0
							&& args[i].compareTo("-c") != 0
							&& args[i].compareTo("-m") != 0) {
						errors++;// detecta un error e incrementa el contador
									// para que al final se identifique cuantos
									// errores se presentaron.
					} else // if(args[i].compareTo("-m")==0)
					{
						identifiesArguments(args, i);
					}
					i++;
				}
			} else if (args[0].compareTo("show") == 0) {
				if (args[1].compareTo("-h") != 0
						&& args[1].compareTo("-c") != 0
						&& args[1].compareTo("-m") != 0) {
					errors++;// detecta un error e incrementa el contador para
								// que al final se identifique cuantos errores
								// se presentaron.
				} else {
					identifiesArguments(args, 2);
				}

			} else if (args[0].compareTo("help") == 0
					|| args[0].compareTo("?") == 0) {
				showHelp();
				System.exit(-1);
			} else {
				showHelp();
				System.out
						.println("\nError: Debe espicificar un parámetro inicial como 'compare'o 'show'");
				throw new WrongParamException(
						"\nError de parametros del comando CODILOC");
			}
		}
		if (ValidateFile(modifiedFilePath) == false) {
			showHelp();
			System.out
					.println("\nError: El tipo de archivo modificado o la ruta no es válida");
			throw new WrongParamException("\nError de parametros del comando");
		}
		if (errors > 0) {
			showHelp();
			System.out
					.println("\nError: Comando sin parámetros o parámetros incompletos. No se puede identificar el archivo modificado");
			throw new WrongParamException("\nError de parametros del comando");

		}
	}

	/**
	 * @throws FileManagerException
	 *             Presenta la ayuda del comando CodiLoc
	 */
	private static void showHelp() throws FileManagerException {
		
		java.io.File helpFile = new java.io.File(
				"\\VersionManager\\codiloc\\Help_EN.txt");
		FileManagerFacade fread = FileManagerFacade.getInstance();
		List<String> helpStream = null;
		helpStream = fread.getLinesFromFile(helpFile);
		for (String linea : helpStream) {
			System.out.println(linea);
		}

	}

	

	private static boolean ValidateFile(String rutaModificado) {
		File fileModificado = new File(rutaModificado);
		String nameFile = fileModificado.getName();
		// System.out.println("Nombre del Archivo: " + nameFile);
		// String[] fileExtension = nameFile.endsWith(PERMITTED_EXTENSION);

		if (nameFile.endsWith(PERMITTED_EXTENSION)) {
			return true;
		}

		return false;
	}

	private static void identifiesArguments(String[] args, int i) {
		switch (args[i]) {
		case "-m":
			modifiedFilePath = args[i + 1];
			break;
		case "-u":
			user = args[i + 1];
			break;
		case "-c":
			comment = args[i + 1];
			break;

		default:
			break;
		}
	}
}
