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
	 * textLine - L�nea de c�digo.
	 */
	private String textLine;

	/**
	 * textLineNumber - Ubicaci�n en el archivo de la l�nea de c�digo.
	 */
	private int textLineNumber;

	/**
	 * Constructor con argumentos
	 * 
	 * @param textLine
	 *            La l�nea de c�digo
	 * @param textLineNumber
	 *            La ubicaci�n en el archivo de la l�nea de c�digo.
	 */
	public ComparedLine(String textLine, int textLineNumber) {
		this.textLine = textLine;
		this.textLineNumber = textLineNumber;
	}

	/**
	 * Obtiene {@link #textLine}
	 * 
	 * @return {@link #textLine}
	 */
	public String getTextLine() {
		return textLine;
	}

	/**
	 * Establece {@link #textLine}
	 * 
	 * @param textLine
	 *            {@link #textLine} a ser establecido.
	 */
	public void setTextLine(String textLine) {
		this.textLine = textLine;
	}

	/**
	 * Obtiene {@link #textLineNumber}
	 * 
	 * @return {@link #textLineNumber}
	 */
	public int getTextLineNumber() {
		return textLineNumber;
	}

	/**
	 * Establece {@link #textLineNumber}
	 * 
	 * @param textLine
	 *            {@link #textLineNumber} a ser establecido.
	 */
	public void setTextLineNumber(int textLineNumber) {
		this.textLineNumber = textLineNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((textLine == null) ? 0 : textLine.hashCode());
		result = prime * result + textLineNumber;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComparedLine other = (ComparedLine) obj;
		if (textLine == null) {
			if (other.textLine != null)
				return false;
		} else if (!textLine.equals(other.textLine))
			return false;
		if (textLineNumber != other.textLineNumber)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ComparedLine {textLine=" + textLine + ", textLineNumber="
				+ textLineNumber + "}";
	}
}
