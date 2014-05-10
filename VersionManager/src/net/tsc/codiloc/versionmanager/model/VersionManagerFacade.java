package net.tsc.codiloc.versionmanager.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.IOException;
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
import net.tsc.codiloc.loccounter.utils.TextUtils;
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
	 * Obtiene {@link #historyLines}
	 * 
	 * @return {@link #historyLines}
	 */
	public List<String> getHistoryLines() {
		return historyLines;
	}

	/**
	 * Establece {@link #historyLines}
	 * 
	 * @param historyLines
	 *            {@link #historyLines} a ser establecido.
	 */
	public void setHistoryLines(List<String> historyLines) {
		this.historyLines = historyLines;
	}

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
	 *            Usuario que modifica el archivo.
	 * @param comment
	 *            Descripci�n de la modificaci�n.
	 * @return Ruta del archivo de modificaciones.
	 * @throws VersionManagerException
	 *             Si ocurre un error en el manejo de versiones.
	 */
	public String compare(String modifiedFilePath,
			String user, String comment) throws VersionManagerException {

		List<String> originalLines = null;
		List<String> modifiedLines = null;
		List<String> historyLines = null;
		Map<String, String> parametersMap = null;
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

			for (String line : modifiedLines) {
				totalLines += countLOC(line);
			}

			parametersMap = new HashMap<>();
			parametersMap.put(USER_KEY, user);
			parametersMap.put(COMMENT_KEY, comment);
			parametersMap
					.put(TOTAL_LINES_ADDED_KEY, String.valueOf(addedLines));
			parametersMap.put(TOTAL_LINES_DELETED_KEY,
					String.valueOf(deletedLines));
			parametersMap.put(TOTAL_LINES_KEY, String.valueOf(totalLines));

			version = addHeader(parametersMap, historyLines);

			addTags(addedLinesList, historyLines, version, ADDED_ID);
			addTags(deletedLinesList, historyLines, version, DELETED_ID);

			writeBase(modifiedFilePath, modifiedLines);
			this.urlHistoryFile = writeHistory(modifiedFilePath,
					this.historyLines);

		} catch (ComparatorException e) {
			logger.severe(e.getMessage());
			System.exit(-1);
		}

		return "En proceso...";
	}

	/**
	 * Permite presentar losresultados obtenidos al validar el archivo modificado contra su linea base.
	 */
	public void showResult() {
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

		System.out.println("\nDirectorio y archivo de resultados: " + urlHistoryFile);
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

		String directory = filePath.substring(0, filePath.lastIndexOf("/"));
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

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

		String directory = filePath.substring(0, filePath.lastIndexOf("/"));
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

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

	/**
	 * Agrega un encabezado al archivo hist�rico.
	 * 
	 * @param header
	 *            Map con objetos del encabezado
	 * @param historyLines
	 *            Lista de lineas del archivo hist�rico
	 * @throws VersionManagerException
	 */
	public String addHeader(Map<String, String> header,
			List<String> historyLines) throws VersionManagerException {
		if (header == null)
			throw new IllegalArgumentException("header must not be null");
		if (historyLines == null)
			throw new IllegalArgumentException("historyLines must not be null");

		int ver = 0;
		if (historyLines.size() > 0
				&& historyLines.get(0).contains("/*<<---HEADER--->>")) {
			ver = Integer.parseInt(historyLines.get(1).replace("*<<VER:", "")
					.replace(">>", "")) + 1;
		}

		SimpleDateFormat now = new SimpleDateFormat();

		List<String> hdr = new ArrayList<String>();
		hdr.add("/*<<---HEADER--->>");
		hdr.add("*<<VER:" + ver + ">>");
		hdr.add("*<<DATETIME:" + now.format(Calendar.getInstance().getTime())
				+ ">>");

		for (Map.Entry<String, String> entry : header.entrySet()) {
			hdr.add("*<<" + entry.getKey() + ":" + entry.getValue() + ">>");
		}
		hdr.add("*<<---END HEADER--->>");
		hdr.add("*/");
		historyLines.addAll(0, hdr);
		
		this.setHistoryLines(historyLines);

		return ver+"";
	}
	
	/**
	 * Adiciona los Tags de las l�neas adicionadas o modificadas al archivo Log
	 * 
	 * @param comparedLines
	 * 			L�neas comparadas entre dos versiones.
	 * @param historyLines
	 * 			Archivo log que contiene las l�neas adicionadas y eliminadas
	 * @param versionNumber
	 * 			Versi�n del cambio a colocar en el Tag
	 * @param identifier
	 * 			Identifica si se env�a lista de l�neas adicionadas o l�neas eliminadas
	 * @throws VersionManagerException
	 * 				Si ocurre un error en el manejo de versiones.
	 */
	public void addTags (List<ComparedLine> comparedLines, List<String> historyLines, String versionNumber, String identifier) throws VersionManagerException {
		
		String buildTag = "";
		String historyLine = "";
		String textIdentifier = "";
		
		if (comparedLines == null)
			throw new IllegalArgumentException("comparedLines must not be null");
		
		if (historyLines == null)
			throw new IllegalArgumentException("historyLines must not be null");
		
		if(identifier.toUpperCase().equals("A")){
			textIdentifier = "adicionada";
		}
		else{
			textIdentifier = "eliminada";
		}
		
		int totalLinesHistoryLines = historyLines.size();
		
		for(int i = totalLinesHistoryLines - 1; i > 0; i--){
			if(historyLines.get(i).contains("<<---END HEADER--->>")){
				totalLinesHistoryLines = i + 2;
				break;
			}
			totalLinesHistoryLines = 0;
		}
		
		int lineNumber = 0;
		for(ComparedLine line : comparedLines){
			buildTag = "";
			lineNumber = line.getTextLineNumber() + totalLinesHistoryLines;
			historyLine = historyLines.get(lineNumber);
			if(historyLine.contains(line.getTextLine())){
				buildTag = "//<<Versi�n: " + versionNumber + " L�nea " + textIdentifier + ": " + line.getTextLine() + ">>";
				historyLines.set(lineNumber, historyLines.get(lineNumber) + " " + buildTag);
			}
		}		
		setHistoryLines(historyLines);
	}
	/**
	 * Escribe la versi�n comparada en el archivo base.
	 * 
	 * @param modifiedFilePath
	 *            Archivo que se va a comparar.
	 * 
	 * @param modifiedLines
	 *            L�neas producto de la compraci�n.
	 * @throws IllegalArgumentException
	 *             Si <code>modifiedLines</code> es <code>null</code> o
	 *             <code>modifiedLines</code> es vac�o.
	 * @throws VersionManagerException
	 *             Si ocurre un error escribiendo el archivo.
	 */
	private void writeBase(String modifiedFilePath, List<String> modifiedLines)
			throws VersionManagerException {
		writeResultFile(BASE_PATH + modifiedFilePath, modifiedLines);
	}

	/**
	 * Escribe el resultado de comparaci�n en el archivo hist�rico.
	 * 
	 * @param modifiedFilePath
	 *            Archivo que se va a comparar.
	 * 
	 * @param historyLines
	 *            L�neas producto de la compraci�n.
	 * @return Ruta del archivo hist�rico
	 * @throws IllegalArgumentException
	 *             Si <code>modifiedLines</code> es <code>null</code> o
	 *             <code>modifiedLines</code> es vac�o.
	 * @throws VersionManagerException
	 *             Si ocurre un error escribiendo el archivo.
	 */
	private String writeHistory(String modifiedFilePath,
			List<String> historyLines) throws VersionManagerException {
		String historyFile = HISTORY_PATH + modifiedFilePath;
		writeResultFile(historyFile + modifiedFilePath, historyLines);
		return historyFile;
	}

	/**
	 * Escribe el resultado de la comparaci�n en un archivo resultado.
	 * 
	 * @param file
	 *            Archivo a escribir.
	 * @param lines
	 *            L�neas producto de la compraci�n.
	 * @throws VersionManagerException
	 *             Si ocurre un error escribiendo el archivo.
	 */
	private void writeResultFile(String file, List<String> lines)
			throws VersionManagerException {
		if (TextUtils.isBlank(file))
			throw new IllegalArgumentException("file must not be empty");
		if (lines == null)
			throw new IllegalArgumentException("lines must not be null");
		File base = new File(file);
		if (!base.exists()) {
			try {
				base.createNewFile();
			} catch (IOException e) {
				String msg = "A IOException occurred at VersionManagerFacade.writeResultFile";
				logger.severe(msg + "\n" + e);
				throw new VersionManagerException(msg, e);
			}
		}
		FileManagerFacade fileManagerFacade = FileManagerFacade.getInstance();
		try {
			fileManagerFacade.writeLinesToFiles(base, lines);
		} catch (FileManagerException e) {
			String msg = "A FileManagerException occurred at VersionManagerFacade.writeResultFile";
			logger.severe(msg + "\n" + e);
			throw new VersionManagerException(msg, e);
		}
	}
}
