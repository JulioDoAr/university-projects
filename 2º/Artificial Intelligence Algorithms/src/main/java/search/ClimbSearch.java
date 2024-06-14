package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import data.Entity;
import data.Operator;
import data.Position;
import logger.Logger;
import node.HeuristicNode;

/**
 * Algoritmo de escalada de maxima pendiente. </br>
 * Desde un estado, se prueban todos los operadores y se evalúan con h'. Se
 * selecciona el mejor estado (con h') que se transforma en el estado actual. Si
 * los estados nuevos son "peores" que el actual, se para el algoritmo
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class ClimbSearch extends Treatment<HeuristicNode> {

	protected Set<Position> targets;
	protected List<HeuristicNode> openNodes;
	protected List<HeuristicNode> closedNodes;

	public ClimbSearch(String filename) {
		super(filename, "Escalada De Maxima Pendiente");

		log = Logger.getInstance();
		openNodes = new ArrayList<HeuristicNode>();
		closedNodes = new ArrayList<HeuristicNode>();
		targets = new TreeSet<Position>();
	}

	@Override
	protected HeuristicNode doSearch() {
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
					targets.add(new Position(i, j));
			}

		// Generamos el nodo parent
		HeuristicNode originalNode = new HeuristicNode(null, null, boxes, robot);

		// Los añadimos al final de la lista de nodos a recorrer
		openNodes.add(originalNode);
		// Mientras que haya nodos en la lista
		HeuristicNode node = null;
		while (!openNodes.isEmpty()) {
			closedNodes.addAll(openNodes);
			Collections.shuffle(openNodes);
			node = openNodes.get(0);
			openNodes.clear();

			if (isSolution(node))
				return node;
			else {
				List<HeuristicNode> childs = generateChilds(node);
				Collections.sort(childs);
				Collections.reverse(childs);
				if (!childs.isEmpty())
					if (childs.get(0).getHeuristic() > node.getHeuristic())
						for (HeuristicNode child : childs)
							if (child.getHeuristic() >= childs.get(0).getHeuristic())
								if (!openNodes.contains(child) && !closedNodes.contains(child)) {
									increaseGeneratedNodes();
									openNodes.add(child);
								}
			}
		}
		return null;
	}

	/**
	 * Comprobar todas las posibles operaciones a aplicar sobre un nodo y en caso de
	 * ser posible, crea un nodo con esos datos
	 *
	 * @param node
	 * @return
	 */
	@Override
	protected List<HeuristicNode> generateChilds(HeuristicNode node) {
		List<HeuristicNode> nodes = new ArrayList<HeuristicNode>();

		// Recorremos los posibles valores de los operadores
		for (Operator operator : Operator.values())
			// HeuristicNode n = generateChild(operator, node);
			if (canBeApplied(operator, node)) {
				HeuristicNode n = generateChild(operator, node);
				calculateHeuristic(n);
				if (n != null)
					nodes.add(n);
			}
		return nodes;
	}

	/**
	 * Tomaremos como heuristica la distancia total que hay del robot a la primera
	 * caja sin colocar. Si esta colocada añadimos 20 puntos y calculamos la
	 * distancia con la siguiente.
	 *
	 * @param n
	 */
	private void calculateHeuristic(HeuristicNode node) {
		int heuristic = 0;
		boolean hasReduced = false;

		Iterator<Position> it1 = node.getBoxes().iterator();
		Iterator<Position> it2 = targets.iterator();
		while (it1.hasNext()) {
			Position box = it1.next();
			Position target = it2.next();
			if (!hasReduced) {
				heuristic += 20 - calculateDistance(node.getRobot(), box);
				hasReduced = true;
			}
			heuristic += 20 - calculateDistance(box, target);

		}
		node.setHeuristic(heuristic);
	}

	private int calculateDistance(Position robot, Position box) {
		int distance = 0;
		distance += calculateDistance(robot.column, box.column);
		distance += calculateDistance(robot.row, box.row);
		return distance;
	}

	private int calculateDistance(int a, int b) {
		return a > b ? a - b : b - a;
	}

	@Override
	protected HeuristicNode generateChild(Operator operator, HeuristicNode parent) {
		Position robot = parent.getRobot();
		HeuristicNode child = null;
		switch (operator) {
		case A:
			child = new HeuristicNode(parent, Operator.A, parent.getBoxes(), new Position(robot.row - 1, robot.column));
			break;
		case B:
			child = new HeuristicNode(parent, Operator.B, parent.getBoxes(), new Position(robot.row + 1, robot.column));
			break;
		case D:
			child = new HeuristicNode(parent, Operator.D, parent.getBoxes(), new Position(robot.row, robot.column + 1));
			break;
		case I:
			child = new HeuristicNode(parent, Operator.I, parent.getBoxes(), new Position(robot.row, robot.column - 1));
			break;
		case EA:
			child = new HeuristicNode(parent, Operator.EA, parent.getBoxes(),
					new Position(robot.row - 1, robot.column));
			child.moveBox(operator, new Position(robot.row - 1, robot.column));
			break;
		case EB:
			child = new HeuristicNode(parent, Operator.EB, parent.getBoxes(),
					new Position(robot.row + 1, robot.column));
			child.moveBox(operator, new Position(robot.row + 1, robot.column));
			break;
		case ED:
			child = new HeuristicNode(parent, Operator.ED, parent.getBoxes(),
					new Position(robot.row, robot.column + 1));
			child.moveBox(operator, new Position(robot.row, robot.column + 1));
			break;
		case EI:
			child = new HeuristicNode(parent, Operator.EI, parent.getBoxes(),
					new Position(robot.row, robot.column - 1));
			child.moveBox(operator, new Position(robot.row, robot.column - 1));
			break;
		case IA:
			child = new HeuristicNode(parent, Operator.IA, parent.getBoxes(),
					new Position(robot.row - 1, robot.column));
			child.moveBox(operator, new Position(robot.row - 1, robot.column));
			break;
		case IB:
			child = new HeuristicNode(parent, Operator.IB, parent.getBoxes(),
					new Position(robot.row + 1, robot.column));
			child.moveBox(operator, new Position(robot.row + 1, robot.column));
			break;
		case ID:
			child = new HeuristicNode(parent, Operator.ID, parent.getBoxes(),
					new Position(robot.row, robot.column + 1));
			child.moveBox(operator, new Position(robot.row, robot.column + 1));
			break;
		case II:
			child = new HeuristicNode(parent, Operator.II, parent.getBoxes(),
					new Position(robot.row, robot.column - 1));
			child.moveBox(operator, new Position(robot.row, robot.column - 1));
			break;
		}
		return child;
	}

	/**
	 * Comprueba si el nodo es un nodo solucion
	 *
	 * @param node El nodo a comprobar
	 * @return true si es solucion, false si no
	 */
	@Override
	protected boolean isSolution(HeuristicNode node) {
		Iterator<Position> it1 = node.getBoxes().iterator();
		Iterator<Position> it2 = targets.iterator();
		while (it1.hasNext())
			if (!it1.next().equals(it2.next()))
				return false;
		return true;
	}

	@Override
	protected void showMovements(HeuristicNode node) {
		HeuristicNode aux = node;
		while (aux != null) {
			printOriginalData(aux);
			log.writeLine("heuristic=%d", aux.getHeuristic());
			log.writeLine("-------------------------------------------------------------------");
			aux = aux.getParent();
		}
	}
}
