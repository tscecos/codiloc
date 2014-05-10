package net.tsc.codiloc.versionmanager.model;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.tsc.codiloc.filemanager.exception.FileManagerException;
import net.tsc.codiloc.filemanager.model.FileManagerFacade;
import net.tsc.codiloc.loccomparator.exception.ComparatorException;
import net.tsc.codiloc.loccomparator.model.ComparatorFacade;
import net.tsc.codiloc.loccomparator.model.ComparedLine;
import net.tsc.codiloc.loccounter.exception.LOCCounterException;
import net.tsc.codiloc.loccounter.model.LOCFacade;
import net.tsc.codiloc.versionmanager.exception.VersionManagerException;

/**
 * Fachada para el manejo de versiones de código fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class VersionManagerFacade {

	public static final String BASE_PATH = "..\\VersionManager\\codiloc\\base\\";
	public static final String HISTORY_PATH = "..\\VersionManager\\codiloc\\history\\";
	public static final String ADDED_ID = "A";
	public static final String DELETED_ID = "D";
	public static final String USER_KEY = "USER";
	public static final String COMMENT_KEY = "COMMENT";
	public static final String TOTAL_LINES_ADDED_KEY = "TOTAL LINES ADDED";
	public static final String TOTAL_LINES_DELETED_KEY = "TOTAL LINES DELETED";
	public static final String TOTAL_LINES_KEY = "TOTAL LINES";

	/**
	 * logger - Bitácora
	 */
	private static Logger logger = Logger.getLogger(ComparatorFacade.class
			.getName());

	/**
	 * instance - Instancia singleton.
	 */
	private static VersionManagerFacade instance;

	private int addedLines;
	private int deletedLines;
	private int totalLines;
	private List<ComparedLine> addedLinesList;
	private List<ComparedLine> deletedLinesList;
	private List<String> historyLines;

	/**
	 * Inicializa la fachada asegurando el singleton.
	 */
	private VersionManagerFacade() {

	}

	/**
	 * Crea una instancia singleton de <code>VersionManagerFacade</code>.
	 */
	private static synchronized void createInstance() {
		if (instance == null) {
			instance = new VersionManagerFacade();
		}
	}

	/**
	 * Obtiene una instancia singleton de <code>VersionManagerFacade</code>.
	 * 
	 * @return Instancia singleton de <code>VersionManagerFacade</code>.
	 */
	public static VersionManagerFacade getInstance() {
		createInstance();
		return instance;
	}

	/**
	 * Cuenta el número de líneas lógicas en el código fuente.
	 * 
	 * @param sourceCode
	 *            Código fuente.
	 * @return Número de líneas.
	 * @throws VersionManagerException
	 *             Si ocurre un error en el manejo de versiones.
	 */
	public int countLOC(String sourceCode) throws VersionManagerException {
		LOCFacade counter = LOCFacade.getInstance();
		int loc = 0;
		try {
			loc = counter.count(sourceCode);
		} catch (LOCCounterException e) {
			String msg = "A LOCCounterException occurred at VersionManagerFacade.countLOC";
			logger.severe(msg + "\n" + e);
		}
		return loc;
	}

	/**
	 * Cuenta el número de líneas comparadas entre la versión origen y la
	 * versión modificada del código fuente.
	 * 
	 * @param comparedLine
	 *            Líneas comparadas entre dos versiones.
	 * @return Número de líneas comparadas.
	 * @throws IllegalArgumentException
	 *             Si <code>comparedLine</code> es <code>null</code>,
	 */
	public int countComparedLOC(List<ComparedLine> comparedLine)
			throws VersionManagerException {
		int lines = 0;

		if (comparedLine == null)
			throw new IllegalArgumentException("comparedLine must not be null");

		for (ComparedLine line : comparedLine) {
			lines += countLOC(line.getTextLine());
		}

		return lines;
	}

	/**
	 * Compara dos versiones de código fuente.
	 * 
	 * @param modifiedFilePath
     *            Ruta del archivo fuente modificado.
     * @param user
     * 			  Usuario que modifica el archivo.
     * @param comment
     * 			  Descripción de la modificación. 
	 * @return Ruta del archivo de modificaciones.
	 * @throws VersionManagerException
	 *             Si ocurre un error en el manejo de versiones.
	 */
	public String compareVersions(String modifiedFilePath,
			String user, String comment) throws VersionManagerException {
		
		List<String> originalLines = null;
		List<String> modifiedLines = null;
		List<String> historyLines = null;
		Map<String,String> parametersMap = null;
		String version = "";
		
		ComparatorFacade comparator = ComparatorFacade.getInstance();
		FileManagerFacade fileManager = FileManagerFacade.getInstance();

		File modifiedFile = new File(modifiedFilePath);
		
		try {
			historyLines = loadHistory(modifiedFilePath);
			originalLines = loadBase(modifiedFilePath);
			modifiedLines = fileManager.getLinesFromFile(modifiedFile);
		} catch (FileManagerException e) {
			logger.severe(e.getMessage());
		}
		
		try {
			addedLinesList = comparator.getAddedLOC(originalLines,
					modifiedLines);
			deletedLinesList = comparator.getDeletedLOC(originalLines,
					modifiedLines);

			addedLines = countComparedLOC(addedLinesList);
			deletedLines = countComparedLOC(deletedLinesList);
			
			for(String line : modifiedLines){
				totalLines += countLOC(line);
			}
			
			parametersMap = new HashMap<>();
			parametersMap.put(USER_KEY, user);
			parametersMap.put(COMMENT_KEY, comment);
			parametersMap.put(TOTAL_LINES_ADDED_KEY, String.valueOf(addedLines));
			parametersMap.put(TOTAL_LINES_DELETED_KEY, String.valueOf(deletedLines));
			parametersMap.put(TOTAL_LINES_KEY, String.valueOf(totalLines));
			
			version = addHeader(parametersMap,historyLines);
			
			addTags(addedLinesList,historyLines,version,ADDED_ID);
			addTags(deletedLinesList,historyLines,version,DELETED_ID);
			
			writeBase(modifiedLines);
			this.urlHistoryFile = writeHistory(this.historyLines);
			
		} catch (ComparatorException e) {
			logger.severe(e.getMessage());
			System.exit(-1);
		}

		return "En proceso...";
	}

	public void print() {
		System.out.println("RESULTADO DE LA COMPARACION");
		System.out.println("\nLíneas adicionadas: " + addedLines);

		for (ComparedLine line : addedLinesList) {
			System.out.println(line.getTextLineNumber() + " - "
					+ line.getTextLine());
		}

		System.out.println("\nLíneas eliminadas: " + deletedLines);

		for (ComparedLine line : deletedLinesList) {
			System.out.println(line.getTextLineNumber() + " - "
					+ line.getTextLine());
		}

		System.out.println("\nLíneas totales: " + totalLines);
	}

	/**
	 * Método que cargará en memoria el archivo base.
	 * 
	 * @param fileName
	 *            Nombre y extensión del archivo que se va a comparar
	 * @return Líneas del archivo base
	 * @throws VersionManagerException
	 */
	public List<String> loadBase(String fileName)
			throws VersionManagerException {

		File baseFile = new File(BASE_PATH + fileName);

		try {
			return loadFile(baseFile);
		} catch (FileManagerException e) {
			throw new VersionManagerException("Exception loading base file", e);
		}

	}

	/**
	 * Método que cargará en memoria el archivo histórico.
	 * 
	 * @param fileName
	 *            Nombre y extensión del archivo que se va a comparar
	 * @return Líneas del archivo histórico
	 * @throws VersionManagerException
	 */
	public List<String> loadHistory(String fileName)
			throws VersionManagerException {

		File historyFile = new File(HISTORY_PATH + fileName);
		try {
			return loadFile(historyFile);
		} catch (FileManagerException e) {
			throw new VersionManagerException("Exception loading history file",
					e);
		}
	}

	/**
	 * Método que carga el contenido de un archivo en una lista de líneas.
	 * 
	 * @param file
	 *            Archivo a cargar
	 * @return Líneas del archivo
	 * @throws FileManagerException
	 */
	private List<String> loadFile(File file) throws FileManagerException {

		FileManagerFacade fileManager = FileManagerFacade.getInstance();

		if (file.exists()) {
			try {
				return fileManager.getLinesFromFile(file);
			} catch (FileManagerException fme) {
				throw fme;
			}
		} else {
			return null;
		}

	}

}
