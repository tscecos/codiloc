package net.tsc.codiloc.loccomparator.model;

/**
 * Clase que representa una l�nea de c�digo comparada y su ubicaci�n dentro del
 * archivo fuente.
 * 
 * @author Carolina Benavides <gc.benavides10@uniandes.edu.co>
 * @version 1.0
 */
public class ComparedLine {

	/**
	 * L�nea de c�digo.
	 */
	private String textLine;
	/**
	 * Ubicaci�n en el archivo de la l�nea de c�digo.
	 */
	private int textLineNumber;
	private String addLine;
	private int addLineNumber;
	
	/**
	 * Constructor con argumentos
	 * @param textLine La l�nea de c�digo
	 * @param textLineNumber La ubicaci�n en el archivo de la l�nea de c�digo.
	 */
	public ComparedLine(String textLine, int textLineNumber){
		this.textLine = textLine;
		this.textLineNumber = textLineNumber;
	}

	/**
	 * @return L�nea de c�digo
	 */
	public String getTextLine() {
		return textLine;
	}

	/**
	 * @param textLine
	 *            L�nea de c�digo
	 */
	public void setTextLine(String textLine) {
		this.textLine = textLine;
	}

	/**
	 * @return Ubicaci�n de la l�nea de c�digo
	 */
	public int getTextLineNumber() {
		return textLineNumber;
	}
	
	/**
	 * @param textLineNumber
	 *            Ubicaci�n de la l�nea de c�digo
	 */

}
