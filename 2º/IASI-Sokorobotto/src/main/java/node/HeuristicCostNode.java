package node;

import java.util.Set;

import data.Operator;
import data.Position;

/**
 * Nodo heuristico con coste.
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class HeuristicCostNode extends Node<HeuristicCostNode> implements Comparable<HeuristicCostNode> {

	private int heuristic;
	private int cost;

	public HeuristicCostNode(HeuristicCostNode parent, Operator operator, Set<Position> boxes, Position robot) {
		super(parent, operator, boxes, robot);
	}

	@Override
	public int compareTo(HeuristicCostNode o) {
		int comparation = Integer.compare((heuristic + cost), (o.getHeuristic() + o.getCost()));
		if (comparation == 0)
			if (!this.equals(o))
				comparation = -1;
		return comparation;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("operator=" + operator + ", ");
		sb.append("heuristic=" + heuristic + ", ");
		sb.append("cost=" + cost + ", ");
		sb.append("robot=" + robot.toString() + ", ");
		sb.append("boxes=" + boxes);
		return sb.toString();
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getF() {
		return heuristic + cost;
	}
}
