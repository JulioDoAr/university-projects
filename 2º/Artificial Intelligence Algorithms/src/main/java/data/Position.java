package data;

/**
 * Conjunto de coordenadas que puede ocupar una entidad en el tablero
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class Position implements Comparable<Position> {

	public int row;
	public int column;

	public Position(int x, int y) {
		super();
		this.row = x;
		this.column = y;
	}

	/**
	 * Constructor para clonar sin compartir referencia
	 *
	 * @param pos Posicion a clonar
	 */
	public Position(Position pos) {
		row = pos.row;
		column = pos.column;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position))
			return false;
		Position pos = (Position) obj;

		int sol = Integer.compare(row, pos.row);
		if (sol == 0)
			sol = Integer.compare(column, pos.column);
		return sol == 0;
	}

	@Override
	public int compareTo(Position o) {
		int value = Integer.compare(row, o.row);
		if (value == 0)
			value = Integer.compare(column, o.column);
		return value;
	}

	@Override
	public String toString() {
		return "{row=" + row + ", column=" + column + "}";
	}

}
