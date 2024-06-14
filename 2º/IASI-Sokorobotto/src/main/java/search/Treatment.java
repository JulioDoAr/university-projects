package search;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

import data.Entity;
import data.Operator;
import data.Position;
import logger.Logger;
import node.Node;
import reader.FileReader;

/**
 * Estructura basica de un algoritmo
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 * @param <T> Tipo de nodo que generara el algoritmo
 */
public abstract class Treatment<T extends Node> {

	/** The name. */
	protected String name;

	/** The panel name. */
	protected String panelName;

	/** The log. */
	protected Logger log;

	/** The init time. */
	private long initTime;

	/** The finish time. */
	private long finishTime;

	/** The generated nodes. */
	private int generatedNodes;

	/** The original data. */
	protected int[][] originalData;

	/** The solution. */
	T solution;

	/**
	 * Instantiates a new treatment.
	 *
	 * @param filename the filename to load the panel
	 * @param name     the name of the algorithm
	 */
	public Treatment(String filename, String name) {
		log = Logger.getInstance();
		this.name = name;
		this.panelName = filename;
		loadData();
	}

	/**
	 * Load data.
	 */
	private void loadData() {
		try {
			URL url = Treatment.class.getClassLoader().getResource(panelName);
			FileReader reader = FileReader.getInstance();
			originalData = reader.readSquareMatrix(new File(url.toURI()));
		} catch (Exception e) {
		}
	}

	/**
	 * Execute the algorithm.
	 */
	public void execute() {
		initTime = System.currentTimeMillis();
		solution = doSearch();
		finishTime = System.currentTimeMillis();
	}

	/**
	 * Check if an operation can be applied to a node
	 *
	 * @param operator the operator
	 * @param original the node
	 * @return true, if successful
	 */
	protected boolean canBeApplied(Operator operator, T original) {
		Position robot = original.getRobot();
		Position aux = null;
		switch (operator) {
		case A:
			return (original.getOperator() != Operator.IB && original.getOperator() != Operator.EA
					&& originalData[robot.row - 1][robot.column] != Entity.Wall.getValue()
					&& !original.getBoxes().contains(new Position(robot.row - 1, robot.column)));
		case B:
			return (original.getOperator() != Operator.IA && original.getOperator() != Operator.EB
					&& originalData[robot.row + 1][robot.column] != Entity.Wall.getValue()
					&& !original.getBoxes().contains(new Position(robot.row + 1, robot.column)));
		case D:
			return (original.getOperator() != Operator.II && original.getOperator() != Operator.ED
					&& originalData[robot.row][robot.column + 1] != Entity.Wall.getValue()
					&& !original.getBoxes().contains(new Position(robot.row, robot.column + 1)));
		case I:
			return (original.getOperator() != Operator.ID && original.getOperator() != Operator.EI
					&& originalData[robot.row][robot.column - 1] != Entity.Wall.getValue()
					&& !original.getBoxes().contains(new Position(robot.row, robot.column - 1)));
		case EA:
			if (original.getOperator() != Operator.B && robot.row < 2)
				return false;

			for (Position box : (Set<Position>) original.getBoxes()) {
				if (box.column == robot.column && box.row == robot.row - 1)
					aux = box;
				if (box.column == robot.column && box.row == robot.row - 2)
					return false;
			}

			// Compruebo que encima de la caja no hay un muro
			return (aux != null && originalData[robot.row - 2][robot.column] != Entity.Wall.getValue());
		case EB:
			if (original.getOperator() != Operator.A && robot.row > originalData.length - 3)
				return false;

			for (Position box : (Set<Position>) original.getBoxes()) {
				if (box.column == robot.column && box.row == robot.row + 1)
					aux = box;
				if (box.column == robot.column && box.row == robot.row + 2)
					return false;
			}
			return (aux != null && originalData[robot.row + 2][robot.column] != Entity.Wall.getValue());
		case ED:
			// Comprobamos que no este cerca de un borde
			if (original.getOperator() != Operator.I && robot.column > originalData.length - 3)
				return false;

			// Comprobamos que al lado del robot haya una caja y detras este vacio
			for (Position box : (Set<Position>) original.getBoxes()) {
				if (box.column == robot.column + 1 && box.row == robot.row)
					aux = box;
				if (box.column == robot.column + 2 && box.row == robot.row)
					return false;
			}
			return (aux != null && originalData[robot.row][robot.column + 2] != Entity.Wall.getValue());
		case EI:
			if (original.getOperator() != Operator.D && robot.column < 2)
				return false;

			for (Position box : (Set<Position>) original.getBoxes()) {
				if (box.column == robot.column - 1 && box.row == robot.row)
					aux = box;
				if (box.column == robot.column - 2 && box.row == robot.row)
					return false;
			}
			return (aux != null && originalData[robot.row][robot.column - 2] != Entity.Wall.getValue());
		case IA:
			if (original.getOperator() != Operator.B && robot.row < 2)
				return false;

			for (Position box : (Set<Position>) original.getBoxes())
				if (box.column == robot.column && box.row == robot.row - 1)
					return true;
			break;
		case IB:
			if (original.getOperator() != Operator.A && robot.row > originalData.length - 3)
				return false;
			for (Position box : (Set<Position>) original.getBoxes())
				if (box.column == robot.column && box.row == robot.row + 1)
					return true;
			break;
		case ID:
			if (original.getOperator() != Operator.I && robot.column > originalData.length - 3)
				return false;
			for (Position box : (Set<Position>) original.getBoxes())
				if (box.column == robot.column + 1 && box.row == robot.row)
					return true;
			break;
		case II:
			if (original.getOperator() != Operator.D && robot.column < 2)
				return false;
			for (Position box : (Set<Position>) original.getBoxes())
				if (box.column == robot.column - 1 && box.row == robot.row)
					return true;
			break;
		}
		return false;
	}

	/**
	 * Check if a node is a complete solution of the problem
	 *
	 * @param node node to check
	 * @return true if is solution, false if not
	 */
	protected abstract boolean isSolution(T node);

	/**
	 * Do search.
	 *
	 * @return the t
	 */
	protected abstract T doSearch();

	/**
	 * Generate a list of child's of a determined node. Each child is an operation
	 * applied to the node.
	 *
	 * @param node the node
	 * @return the list of child's
	 */
	protected abstract List<T> generateChilds(T node);

	/**
	 * Generate child.
	 *
	 * @param operator the operator
	 * @param parent   the parent
	 * @return the t
	 */
	protected abstract T generateChild(Operator operator, T parent);

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public long getTime() {
		return finishTime - initTime;
	}

	/**
	 * Increase generated nodes.
	 */
	protected void increaseGeneratedNodes() {
		generatedNodes++;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the panel name.
	 *
	 * @return the panel name
	 */
	public String getPanelName() {
		return panelName;
	}

	/**
	 * Gets the generated nodes.
	 *
	 * @return the generated nodes
	 */
	public int getGeneratedNodes() {
		return generatedNodes;
	}

	/**
	 * Gets the solution.
	 *
	 * @return the solution
	 */
	public T getSolution() {
		return solution;
	}

	/**
	 * Show all the movements relited to achieve the solution.
	 */
	public void showMovements() {
		if (solution != null)
			showMovements(solution);
	}

	/**
	 * Show movements.
	 *
	 * @param node the node
	 */
	protected abstract void showMovements(T node);

	protected void printOriginalData(T node) {
		for (int row = 0; row < originalData.length; row++) {
			for (int column = 0; column < originalData.length; column++)
				if (originalData[row][column] == 1)
					log.write("\u2588\u2588");
				else if (node.getBoxes().contains(new Position(row, column)))
					log.write("Ca");
				else if (node.getRobot().equals(new Position(row, column)))
					log.write("Ro");
				else if (originalData[row][column] == 3)
					log.write("De");
				else
					log.write("  ");
			log.writeLine("");
		}
	}

	/**
	 * Show original data.
	 */
	public void showOriginalData() {
		for (int row = 0; row < originalData.length; row++) {
			for (int column = 0; column < originalData.length; column++)
				switch (originalData[row][column]) {
				case 1:
					log.write("\u2588\u2588");
					break;
				case 2:
					log.write("Ca");
					break;
				case 3:
					log.write("De");
					break;
				case 4:
					log.write("Ro");
					break;
				default:
					log.write("  ");
					break;
				}
			log.writeLine("");
		}
	}
}
