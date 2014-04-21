package net.tsc.codiloc.loccomparator.model;

/**
 * Clase que representa una línea de código comparada y su ubicación dentro del
 * archivo fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class ComparedLine {

	/**
	 * Línea de código.
	 */
	private String textLine;
	/**
	 * Ubicación en el archivo de la línea de código.
	 */
	private int textLineNumber;
	private String addLine;
	private int addLineNumber;
	
	/**
	 * Constructor con argumentos
	 * @param textLine La línea de código
	 * @param textLineNumber La ubicación en el archivo de la línea de código.
	 */
	public ComparedLine(String textLine, int textLineNumber){
		this.textLine = textLine;
		this.textLineNumber = textLineNumber;
	}

	/**
	 * @return Línea de código
	 */
	public String getTextLine() {
		return textLine;
	}

	/**
	 * @param textLine
	 *            Línea de código
	 */
	public void setTextLine(String textLine) {
		this.textLine = textLine;
	}

	/**
	 * @return Ubicación de la línea de código
	 */
	public int getTextLineNumber() {
		return textLineNumber;
	}
	
	/**
	 * @param textLineNumber
	 *            Ubicación de la línea de código
	 */

}
