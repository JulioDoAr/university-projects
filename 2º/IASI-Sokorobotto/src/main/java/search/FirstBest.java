package search;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import data.Entity;
import data.Operator;
import data.Position;
import logger.Logger;
import node.HeuristicNode;

/**
 * Algoritmo de escalada simple o primero mejor. </br>
 * Desde un estado, se aplica un operador posible aleatorio y, si tiene mejor
 * heurística que el actual, se avanza hacia el
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class FirstBest extends Treatment<HeuristicNode> {

	protected Set<Position> targets;

	public FirstBest(String filename) {
		super(filename, "Escalada Simple");

		log = Logger.getInstance();
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
		HeuristicNode node = new HeuristicNode(null, null, boxes, robot);
		calculateHeuristic(node);

		Operator[] operators = Operator.values();
		shuffleFisherYates(operators);

		boolean salir = false;
		boolean updated = false;
		while (!salir) {
			updated = false;
			// Generamos el siguiente nodo;
			for (int i = 0; i < operators.length && !updated; i++) {
				Operator operator = operators[i];

				if (canBeApplied(operator, node)) {
					HeuristicNode n = generateChild(operator, node);
					calculateHeuristic(n);
					increaseGeneratedNodes();
					if (node.getHeuristic() < n.getHeuristic()) {
						node = n;
						updated = true;
					}
				}
			}
			if (!updated || isSolution(node))
				salir = true;
		}

		return updated ? node : null;
	}

	private static void shuffleFisherYates(Operator[] array) {
		int index;
		Operator aux;
		Random random = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			if (index != i) {
				aux = array[i];
				array[i] = array[index];
				array[index] = aux;
			}
		}
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

	@Override
	protected List<HeuristicNode> generateChilds(HeuristicNode node) {
		return null;
	}
}
