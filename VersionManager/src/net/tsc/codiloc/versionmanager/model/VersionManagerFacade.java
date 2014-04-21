package net.tsc.codiloc.versionmanager.model;

import java.io.File;
import java.util.List;
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
	public int countComparedLOC(List<ComparedLine> comparedLine) throws VersionManagerException{
		int lines = 0;
		
		if (comparedLine == null)
			throw new IllegalArgumentException("comparedLine must not be null");
		
		for(ComparedLine line : comparedLine){
			lines += countLOC(line.getTextLine());
		}
		
		return lines;
	}

	/**
	 * Compara dos versiones de código fuente.
	 * 
	 * @param originalFilePath
	 *            Ruta del archivo fuente original.
	 * @param modifiedFilePath
//	 *            Ruta del archivo fuente modificado.
	 * @return Ruta del archivo de modificaciones.
	 * @throws VersionManagerException
	 *             Si ocurre un error en el manejo de versiones.
	 */
	public String compareVersions(String originalFilePath,
			String modifiedFilePath) throws VersionManagerException {
		
		List<String> originalLines = null;
		List<String> modifiedLines = null;
		
		ComparatorFacade comparator = ComparatorFacade.getInstance();
		FileManagerFacade fileManager = FileManagerFacade.getInstance();

		File originalFile = new File(originalFilePath);
		File modifiedFile = new File(modifiedFilePath);
		
		try {
			originalLines = fileManager.getLinesFromFile(originalFile);
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
			System.out.println(line.getTextLineNumber() + " - " + line.getTextLine());
		}

		System.out.println("\nLíneas eliminadas: " + deletedLines);

		for (ComparedLine line : deletedLinesList) {
			System.out.println(line.getTextLineNumber() + " - " + line.getTextLine());
		}

		System.out.println("\nLíneas totales: " + totalLines);
	}
}
