package node;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import data.Operator;
import data.Position;

/**
 * Nodo generico. Contiene todos los datos basicos de un nodo
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class Node<T> {

	protected T parent;
	protected Operator operator;
	protected Set<Position> boxes;
	protected Position robot;

	public Node(T parent, Operator operator, Set<Position> boxes, Position robot) {
		this.parent = parent;
		this.operator = operator;
		this.boxes = new TreeSet<Position>();
		for (Position box : boxes)
			this.boxes.add(new Position(box));
		this.robot = robot;
	}

	public T getParent() {
		return parent;
	}

	public Operator getOperator() {
		return operator;
	}

	public Set<Position> getBoxes() {
		return boxes;
	}

	public Position getRobot() {
		return robot;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Node))
			return false;
		Node node = (Node) obj;
		if (!robot.equals(node.getRobot()))
			return false;

		Iterator<Position> i1 = boxes.iterator();
		Iterator<Position> i2 = node.getBoxes().iterator();

		// TODO: Si entra aqui, es que los nodos no tienen el mismo numero de cajas...
		if (boxes.size() != node.getBoxes().size())
			return false;

		while (i1.hasNext())
			if (!i1.next().equals(i2.next()))
				return false;

		return true;
	}

	/**
	 * Mueve la caja en la posicion indicada dependiendo del operador aplicado
	 *
	 * @param operator Operador a aplicar
	 * @param position Posicion de la caja a mover
	 */
	public void moveBox(Operator operator, Position position) {
		for (Position box : boxes)
			if (box.equals(position)) {
				switch (operator) {
				case EA:
				case IB:
					box.row--;
					break;
				case EB:
				case IA:
					box.row++;
					break;
				case ED:
				case II:
					box.column++;
					break;
				case EI:
				case ID:
					box.column--;
					break;
				}
				return;
			}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("operator=" + operator + ", \t");
		sb.append("robot=" + robot.toString() + ", \t");
		sb.append("boxes=" + boxes + "\t");
		return sb.toString();
	}
}
