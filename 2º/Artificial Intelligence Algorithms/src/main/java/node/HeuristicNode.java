package node;

import java.util.Iterator;
import java.util.Set;

import data.Operator;
import data.Position;

/**
 * Nodo heuristico sin coste
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class HeuristicNode extends Node<HeuristicNode> implements Comparable<HeuristicNode> {

	protected int heuristic;

	public HeuristicNode(HeuristicNode parent, Operator operator, Set<Position> boxes, Position robot) {
		super(parent, operator, boxes, robot);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HeuristicNode))
			return false;
		HeuristicNode node = (HeuristicNode) obj;
		if (!robot.equals(node.getRobot()))
			return false;

		Iterator<Position> i1 = boxes.iterator();
		Iterator<Position> i2 = node.getBoxes().iterator();
		while (i1.hasNext())
			if (!i1.next().equals(i2.next()))
				return false;

		return true;
	}

	@Override
	public int compareTo(HeuristicNode o) {
		int comparation = Integer.compare(heuristic, o.getHeuristic());
		if (comparation == 0)
			if (!this.equals(o))
				comparation = -1;
		return comparation;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("operator=" + operator + ", ");
		sb.append("heuristic=" + heuristic + ", ");
		sb.append("robot=" + robot.toString() + ", ");
		sb.append("boxes=" + boxes);
		return sb.toString();
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
}
