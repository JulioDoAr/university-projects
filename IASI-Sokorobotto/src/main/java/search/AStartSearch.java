package search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import data.Entity;
import data.Operator;
import data.Position;
import logger.Logger;
import node.HeuristicCostNode;

/**
 * Algoritmo de A*. </br>
 * Desde un estado, se prueban todos los operadores y se evalúan con f'. Se
 * ordenan los estados a traves de f' minimizante y se expanden en ese orden,
 * anadiendo los nodos ya espandidos a la lista de nodos cerrados
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class AStartSearch extends Treatment<HeuristicCostNode> {

	protected Set<Position> targets;
	protected TreeSet<HeuristicCostNode> openNodes;
	protected List<HeuristicCostNode> closedNodes;

	public AStartSearch(String filename) {
		super(filename, "A*");
		log = Logger.getInstance();
		openNodes = new TreeSet<HeuristicCostNode>();
		closedNodes = new ArrayList<HeuristicCostNode>();
		targets = new TreeSet<Position>();
	}

	@Override
	protected HeuristicCostNode doSearch() {
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
		HeuristicCostNode originalNode = new HeuristicCostNode(null, null, boxes, robot);
		calculateHeuristic(originalNode);

		// Los añadimos al final de la lista de nodos a recorrer
		openNodes.add(originalNode);
		// Mientras que haya nodos en la lista
		HeuristicCostNode node = null;
		while (!openNodes.isEmpty()) {
			node = openNodes.pollFirst();
			closedNodes.add(node);

			if (isSolution(node))
				return node;
			else {
				List<HeuristicCostNode> childs = generateChilds(node);

				HeuristicCostNode bestChild = null;
				for (HeuristicCostNode child : childs)
					if (!openNodes.contains(child) && !closedNodes.contains(child)) {
						increaseGeneratedNodes();
						openNodes.add(child);
					}
			}
		}
		return null;
	}

	/**
	 * Comprueba si el nodo es un nodo solucion
	 *
	 * @param node El nodo a comprobar
	 * @return true si es solucion, false si no
	 */
	@Override
	protected boolean isSolution(HeuristicCostNode node) {
		Iterator<Position> it1 = node.getBoxes().iterator();
		Iterator<Position> it2 = targets.iterator();
		while (it1.hasNext())
			if (!it1.next().equals(it2.next()))
				return false;
		return true;
	}

	@Override
	protected List<HeuristicCostNode> generateChilds(HeuristicCostNode node) {
		List<HeuristicCostNode> nodes = new ArrayList<HeuristicCostNode>();

		// Recorremos los posibles valores de los operadores
		for (Operator operator : Operator.values())
			if (canBeApplied(operator, node)) {
				HeuristicCostNode n = generateChild(operator, node);
				calculateHeuristic(n);
				calculateCost(n);
				if (n != null)
					nodes.add(n);
			}
		return nodes;
	}

	@Override
	protected HeuristicCostNode generateChild(Operator operator, HeuristicCostNode parent) {
		Position robot = parent.getRobot();
		HeuristicCostNode child = null;
		switch (operator) {
		case A:
			child = new HeuristicCostNode(parent, Operator.A, parent.getBoxes(),
					new Position(robot.row - 1, robot.column));
			break;
		case B:
			child = new HeuristicCostNode(parent, Operator.B, parent.getBoxes(),
					new Position(robot.row + 1, robot.column));
			break;
		case D:
			child = new HeuristicCostNode(parent, Operator.D, parent.getBoxes(),
					new Position(robot.row, robot.column + 1));
			break;
		case I:
			child = new HeuristicCostNode(parent, Operator.I, parent.getBoxes(),
					new Position(robot.row, robot.column - 1));
			break;
		case EA:
			child = new HeuristicCostNode(parent, Operator.EA, parent.getBoxes(),
					new Position(robot.row - 1, robot.column));
			child.moveBox(operator, new Position(robot.row - 1, robot.column));
			break;
		case EB:
			child = new HeuristicCostNode(parent, Operator.EB, parent.getBoxes(),
					new Position(robot.row + 1, robot.column));
			child.moveBox(operator, new Position(robot.row + 1, robot.column));
			break;
		case ED:
			child = new HeuristicCostNode(parent, Operator.ED, parent.getBoxes(),
					new Position(robot.row, robot.column + 1));
			child.moveBox(operator, new Position(robot.row, robot.column + 1));
			break;
		case EI:
			child = new HeuristicCostNode(parent, Operator.EI, parent.getBoxes(),
					new Position(robot.row, robot.column - 1));
			child.moveBox(operator, new Position(robot.row, robot.column - 1));
			break;
		case IA:
			child = new HeuristicCostNode(parent, Operator.IA, parent.getBoxes(),
					new Position(robot.row - 1, robot.column));
			child.moveBox(operator, new Position(robot.row - 1, robot.column));
			break;
		case IB:
			child = new HeuristicCostNode(parent, Operator.IB, parent.getBoxes(),
					new Position(robot.row + 1, robot.column));
			child.moveBox(operator, new Position(robot.row + 1, robot.column));
			break;
		case ID:
			child = new HeuristicCostNode(parent, Operator.ID, parent.getBoxes(),
					new Position(robot.row, robot.column + 1));
			child.moveBox(operator, new Position(robot.row, robot.column + 1));
			break;
		case II:
			child = new HeuristicCostNode(parent, Operator.II, parent.getBoxes(),
					new Position(robot.row, robot.column - 1));
			child.moveBox(operator, new Position(robot.row, robot.column - 1));
			break;
		}
		return child;
	}

	/**
	 * Tomaremos como heuristica la distancia total que hay del robot a la primera
	 * caja sin colocar. Si esta colocada añadimos 20 puntos y calculamos la
	 * distancia con la siguiente.
	 *
	 * @param n
	 */
	private void calculateHeuristic(HeuristicCostNode node) {
		int heuristic = 0;
		boolean hasReduced = false;

		Iterator<Position> it1 = node.getBoxes().iterator();
		Iterator<Position> it2 = targets.iterator();
		while (it1.hasNext()) {
			Position box = it1.next();
			Position target = it2.next();
			if (!box.equals(target)) {
				if (!hasReduced) {
					heuristic += calculateDistance(node.getRobot(), box);
					hasReduced = true;
				}
				heuristic += calculateDistance(box, target);
			}
		}
		node.setHeuristic(heuristic);
	}

	private void calculateCost(HeuristicCostNode node) {
		node.setCost(node.getParent().getCost() + 1);
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
	protected void showMovements(HeuristicCostNode node) {
		HeuristicCostNode aux = node;
		while (aux != null) {
			log.writeLine("Operador=%s", aux.getOperator());
			printOriginalData(aux);
			log.writeLine("heuristic=%d", aux.getHeuristic());
			log.writeLine("cost=%d", aux.getCost());
			log.writeLine("-------------------------------------------------------------------");
			aux = aux.getParent();
		}
	}

}
