package search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import data.Entity;
import data.Operator;
import data.Position;
import node.BasicNode;

/**
 * Algoritmo de busqueda horizontal de la solucion
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class HorizontalSearch extends Treatment<BasicNode> {

	protected Set<Position> target;
	protected List<BasicNode> openNodes;
	protected List<BasicNode> closedNodes;

	public HorizontalSearch(String filename) {
		super(filename, "Busqueda Horizontal");

		openNodes = new ArrayList<BasicNode>();
		closedNodes = new ArrayList<BasicNode>();
		target = new TreeSet<Position>();
	}

	@Override
	protected BasicNode doSearch() {

		Set<Position> boxes = new TreeSet<Position>();
		Position robot = null;
		// Generamos sus nodos hijos
		for (int i = 0; i < originalData.length; i++)
			for (int j = 0; j < originalData.length; j++) {
				if (Entity.Box.getValue() == originalData[i][j])
					boxes.add(new Position(new Position(i, j)));
				if (Entity.Robot.getValue() == originalData[i][j])
					robot = new Position(i, j);
				if (Entity.Target.getValue() == originalData[i][j])
					target.add(new Position(i, j));
			}

		// Generamos el nodo parent
		BasicNode originalNode = new BasicNode(null, null, boxes, robot);

		// Los añadimos al final de la lista de nodos a recorrer
		openNodes.add(originalNode);

		// Mientras que haya nodos en la lista
		for (int i = 0; i < openNodes.size(); i++) {
			// Cargamos el siguiente nodo
			BasicNode node = openNodes.get(i);

			// Si es solucion, devolvemos verdadero
			if (isSolution(node))
				return node;
			else {
				// Si no es solucion, se generan sus hijos y se añade al final de la lista
				List<BasicNode> childs = generateChilds(node);
				for (BasicNode child : childs)
					if (!openNodes.contains(child)) {
						increaseGeneratedNodes();
						openNodes.add(child);
					}
			}
		}

		return null;
	}

	@Override
	protected List<BasicNode> generateChilds(BasicNode node) {
		List<BasicNode> nodes = new ArrayList<BasicNode>();

		// Recorremos los posibles valores de los operadores
		for (Operator operator : Operator.values())
			if (canBeApplied(operator, node)) {
				BasicNode n = generateChild(operator, node);

				if (n != null)
					nodes.add(n);
			}
		return nodes;
	}

	@Override
	protected boolean isSolution(BasicNode node) {

		Iterator<Position> it1 = node.getBoxes().iterator();
		Iterator<Position> it2 = target.iterator();

		while (it1.hasNext()) {

			Position pos1 = it1.next();
			Position pos2 = it2.next();

			if (!pos1.equals(pos2))
				return false;
		}
		return true;
	}

	@Override
	protected BasicNode generateChild(Operator operator, BasicNode parent) {
		Position robot = parent.getRobot();
		BasicNode child = null;
		switch (operator) {
		case A:
			child = new BasicNode(parent, Operator.A, parent.getBoxes(), new Position(robot.row - 1, robot.column));
			break;
		case B:
			child = new BasicNode(parent, Operator.B, parent.getBoxes(), new Position(robot.row + 1, robot.column));
			break;
		case D:
			child = new BasicNode(parent, Operator.D, parent.getBoxes(), new Position(robot.row, robot.column + 1));
			break;
		case I:
			child = new BasicNode(parent, Operator.I, parent.getBoxes(), new Position(robot.row, robot.column - 1));
			break;
		case EA:
			child = new BasicNode(parent, Operator.EA, parent.getBoxes(), new Position(robot.row - 1, robot.column));
			child.moveBox(operator, new Position(robot.row - 1, robot.column));
			break;
		case EB:
			child = new BasicNode(parent, Operator.EB, parent.getBoxes(), new Position(robot.row + 1, robot.column));
			child.moveBox(operator, new Position(robot.row + 1, robot.column));
			break;
		case ED:
			child = new BasicNode(parent, Operator.ED, parent.getBoxes(), new Position(robot.row, robot.column + 1));
			child.moveBox(operator, new Position(robot.row, robot.column + 1));
			break;
		case EI:
			child = new BasicNode(parent, Operator.EI, parent.getBoxes(), new Position(robot.row, robot.column - 1));
			child.moveBox(operator, new Position(robot.row, robot.column - 1));
			break;
		case IA:
			child = new BasicNode(parent, Operator.IA, parent.getBoxes(), new Position(robot.row - 1, robot.column));
			child.moveBox(operator, new Position(robot.row - 1, robot.column));
			break;
		case IB:
			child = new BasicNode(parent, Operator.IB, parent.getBoxes(), new Position(robot.row + 1, robot.column));
			child.moveBox(operator, new Position(robot.row + 1, robot.column));
			break;
		case ID:
			child = new BasicNode(parent, Operator.ID, parent.getBoxes(), new Position(robot.row, robot.column + 1));
			child.moveBox(operator, new Position(robot.row, robot.column + 1));
			break;
		case II:
			child = new BasicNode(parent, Operator.II, parent.getBoxes(), new Position(robot.row, robot.column - 1));
			child.moveBox(operator, new Position(robot.row, robot.column - 1));
			break;
		}
		return child;
	}

	@Override
	protected void showMovements(BasicNode node) {
		BasicNode aux = node;
		while (aux != null) {
			printOriginalData(aux);
			log.writeLine("-------------------------------------------------------------------");
			aux = aux.getParent();
		}
	}

}
