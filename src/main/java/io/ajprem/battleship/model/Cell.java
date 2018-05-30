package io.ajprem.battleship.model;

/***
 * Class to represent cell on the battleArea.
 * An Area of 3*3 should have maximum 9 cells
 *

 *
 */
public class Cell implements Comparable<Cell> {

	private final char row;
	private final int column;
	private ShipPart part;

	public Cell(final char row, final int column) {
		this.row = row;
		this.column = column;
		setPart(null);
	}

	public Cell(final char row, final int column, final ShipPart part) {
		this.row = row;
		this.column = column;
		this.setPart(part);
	}

	public char getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public ShipPart getPart() {
		return part;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cell other = (Cell) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

	public void setPart(final ShipPart part) {
		this.part = part;
	}

	@Override
	public int compareTo(final Cell o) {
		return toString().compareTo(o.toString());
	}

	@Override
	public String toString() {
		return "" + row + column;
	}

}
