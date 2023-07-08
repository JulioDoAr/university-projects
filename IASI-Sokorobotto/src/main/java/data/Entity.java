package data;

/**
 * Entidad que puede ocupar una casilla. En nuestro caso y en orden, puede estar
 * vacia, con un muro, con una caja, con una posicion objetivo o con el robot.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public enum Entity {

	Void(0), Wall(1), Box(2), Target(3), Robot(4);

	private int value;

	private Entity(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
