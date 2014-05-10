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
 * Fachada para el manejo de versiones de c�digo fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class VersionManagerFacade {

	public static final String BASE_PATH = "\\VersionManager\\codiloc\\base\\";
	public static final String HISTORY_PATH = "\\VersionManager\\codiloc\\history\\";
	public static final String ADDED_ID = "A";
	public static final String DELETED_ID = "D";
	public static final String USER_KEY = "USER";
	public static final String COMMENT_KEY = "COMMENT";
	public static final String TOTAL_LINES_ADDED_KEY = "TOTAL LINES ADDED";
	public static final String TOTAL_LINES_DELETED_KEY = "TOTAL LINES DELETED";
	public static final String TOTAL_LINES_KEY = "TOTAL LINES";

	/**
	 * logger - Bit�cora
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
	private String urlHistoryFile;

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
	 * Cuenta el n�mero de l�neas l�gicas en el c�digo fuente.
	 * 
	 * @param sourceCode
	 *            C�digo fuente.
	 * @return N�mero de l�neas.
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
	 * Cuenta el n�mero de l�neas comparadas entre la versi�n origen y la
	 * versi�n modificada del c�digo fuente.
	 * 
	 * @param comparedLine
	 *            L�neas comparadas entre dos versiones.
	 * @return N�mero de l�neas comparadas.
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
	 * Compara dos versiones de c�digo fuente.
	 * 
	 * @param modifiedFilePath
     *            Ruta del archivo fuente modificado.
     * @param user
     * 			  Usuario que modifica el archivo.
     * @param comment
     * 			  Descripci�n de la modificaci�n. 
	 * @return Ruta del archivo de modificaciones.
	 * @throws VersionManagerException
	 *             Si ocurre un error en el manejo de versiones.
	 */
	public String compare(String modifiedFilePath,
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
		System.out.println("\nL�neas adicionadas: " + addedLines);

		for (ComparedLine line : addedLinesList) {
			System.out.println(line.getTextLineNumber() + " - "
					+ line.getTextLine());
		}

		System.out.println("\nL�neas eliminadas: " + deletedLines);

		for (ComparedLine line : deletedLinesList) {
			System.out.println(line.getTextLineNumber() + " - "
					+ line.getTextLine());
		}

		System.out.println("\nL�neas totales: " + totalLines);
	}

	/**
	 * M�todo que cargar� en memoria el archivo base.
	 * 
	 * @param filePath
	 *            Nombre y extensi�n del archivo que se va a comparar
	 * @return L�neas del archivo base
	 * @throws VersionManagerException
	 */
	public List<String> loadBase(String filePath)
			throws VersionManagerException {

		String directory = filePath.substring(0, filePath.lastIndexOf("\\"));
		String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

		File baseFile = new File(directory + BASE_PATH + fileName);

		try {
			return loadFile(baseFile);
		} catch (FileManagerException e) {
			throw new VersionManagerException("Exception loading base file", e);
		}

	}

	/**
	 * M�todo que cargar� en memoria el archivo hist�rico.
	 * 
	 * @param fileName
	 *            Nombre y extensi�n del archivo que se va a comparar
	 * @return L�neas del archivo hist�rico
	 * @throws VersionManagerException
	 */
	public List<String> loadHistory(String filePath)
			throws VersionManagerException {

		String directory = filePath.substring(0, filePath.lastIndexOf("\\"));
		String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

		File historyFile = new File(directory + HISTORY_PATH + fileName);
		try {
			return loadFile(historyFile);
		} catch (FileManagerException e) {
			throw new VersionManagerException("Exception loading history file",
					e);
		}
	}

	/**
	 * M�todo que carga el contenido de un archivo en una lista de l�neas.
	 * 
	 * @param file
	 *            Archivo a cargar
	 * @return L�neas del archivo
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
