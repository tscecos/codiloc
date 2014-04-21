package net.tsc.codiloc.loccomparator.model;

import java.util.ArrayList;
import java.util.List;

import net.tsc.codiloc.loccomparator.exception.ComparatorException;
import difflib.Chunk;
import difflib.Delta;
import difflib.Delta.TYPE;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * Fachada para la comparación de código fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class ComparatorFacade {

	/**
	 * instance - Instancia singleton.
	 */
	private static ComparatorFacade instance;

	/**
	 * Inicializa la fachada asegurando el singleton.
	 */
	private ComparatorFacade() {
		
	}

	/**
	 * Crea una instancia singleton de <code>ComparatorFacade</code>.
	 */
	private static synchronized void createInstance() {
		if (instance == null) {
			instance = new ComparatorFacade();
		}
	}

	/**
	 * Obtiene una instancia singleton de <code>ComparatorFacade</code>.
	 * 
	 * @return Instancia singleton de <code>ComparatorFacade</code>.
	 */
	public static ComparatorFacade getInstance() {
		createInstance();
		return instance;
	}

	/**
	 * Obtener las líneas de código adicionadas en el archivo modificado.
	 * 
	 * @param originalLines
	 *            Código fuente original
	 * @param modifiedLines
	 *            Código fuente modificado
	 * @return Lista de líneas de código adicionadas
	 * @throws ComparatorException
	 */
	public List<ComparedLine> getAddedLOC(List<String> originalLines,
			List<String> modifiedLines) throws ComparatorException {
		return getComparedLines(originalLines, modifiedLines, TYPE.INSERT);
	}

	/**
	 * Obtener las líneas de código eliminadas en el archivo modificado.
	 * 
	 * @param originalLines
	 *            Código fuente original
	 * @param modifiedLines
	 *            Código fuente modificado
	 * @return Lista de líneas de código eliminadas
	 * @throws ComparatorException
	 */
	public List<ComparedLine> getDeletedLOC(List<String> originalLines,
			List<String> modifiedLines) throws ComparatorException {
		return getComparedLines(originalLines, modifiedLines, TYPE.DELETE);
	}

	/**
	 * Obtiene la lista de líneas comparadas según el tipo de comparación
	 * 
	 * @param originalLines
	 *            Líneas originales
	 * @param modifiedLines
	 *            Líneas comparadas
	 * @param type
	 *            Tipo de comparación
	 * @return Lista de líneas comparadas
	 * @throws ComparatorException
	 */
	private List<ComparedLine> getComparedLines(List<String> originalLines,
			List<String> modifiedLines, Delta.TYPE type)
			throws ComparatorException {
		if (originalLines == null || modifiedLines == null) {
			throw new ComparatorException(
					"Las líneas a comparar no pueden ser nulas");
		}
		Patch patch = DiffUtils.diff(originalLines, modifiedLines);
		List<ComparedLine> comparedLines = new ArrayList<ComparedLine>();
		List<Delta> deltas = patch.getDeltas();
		for (Delta delta : deltas) {
			Chunk chunk = null;
			if (delta.getType() == type) {
				
				if(type.equals(TYPE.DELETE)){
					chunk = delta.getOriginal();
				} else {
					chunk = delta.getRevised();
				}
				
				for (int i = 0; i < chunk.getLines().size(); i++) {
					if(!chunk.getLines().get(i).toString().trim().isEmpty()){
						comparedLines.add(new ComparedLine(chunk.getLines().get(i)
							.toString(), chunk.getPosition() + 1 + i));
					}
				}
			}
		}
		return comparedLines;
	}
}
