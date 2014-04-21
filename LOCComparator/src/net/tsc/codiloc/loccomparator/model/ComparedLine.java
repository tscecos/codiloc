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
	 * textLine - Línea de código.
	 */
	private String textLine;

	/**
	 * textLineNumber - Ubicación en el archivo de la línea de código.
	 */
	private int textLineNumber;

	/**
	 * Constructor con argumentos
	 * 
	 * @param textLine
	 *            La línea de código
	 * @param textLineNumber
	 *            La ubicación en el archivo de la línea de código.
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
