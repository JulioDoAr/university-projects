package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import node.HeuristicNode;

public class HeuristicNodeTest {

	@Test
	public void test_equals() {
		Set<Position> boxes = new TreeSet<Position>();
		boxes.add(new Position(0, 0));
		boxes.add(new Position(1, 2));
		HeuristicNode n1 = new HeuristicNode(null, Operator.A, boxes, new Position(5, 3));
		n1.setHeuristic(1);

		Set<Position> boxes2 = new TreeSet<Position>();
		boxes2.add(new Position(0, 0));
		boxes2.add(new Position(1, 2));
		HeuristicNode n2 = new HeuristicNode(null, Operator.A, boxes2, new Position(5, 3));
		n1.setHeuristic(2);

		Set<Position> boxes3 = new TreeSet<Position>();
		boxes3.add(new Position(1, 2));
		boxes3.add(new Position(1, 0));
		HeuristicNode n3 = new HeuristicNode(null, Operator.A, boxes3, new Position(5, 3));
		n1.setHeuristic(3);

		Set<Position> boxes4 = new TreeSet<Position>();
		boxes4.add(new Position(0, 0));
		boxes4.add(new Position(1, 2));
		HeuristicNode n4 = new HeuristicNode(null, Operator.A, boxes4, new Position(4, 3));
		n1.setHeuristic(4);

		assertTrue(n1.equals(n2));
		assertFalse(n1.equals(n3));
		assertFalse(n1.equals(n4));
	}

	@Test
	public void test_numberOfBoxes() {
		Set<Position> boxes = new TreeSet<Position>();
		boxes.add(new Position(0, 0));
		boxes.add(new Position(1, 2));
		HeuristicNode n1 = new HeuristicNode(null, Operator.A, boxes, new Position(5, 3));

		assertEquals(2, n1.getBoxes().size());
	}

	@Test
	public void test_boxesAreNotSameInstance() {
		Set<Position> boxes = new TreeSet<Position>();
		boxes.add(new Position(0, 0));
		boxes.add(new Position(1, 2));
		HeuristicNode n1 = new HeuristicNode(null, Operator.A, boxes, new Position(5, 3));

		Iterator<Position> i1 = boxes.iterator();
		Iterator<Position> i2 = n1.getBoxes().iterator();
		while (i1.hasNext()) {
			Position p1 = i1.next();
			Position p2 = i2.next();
			assertFalse(p1 == p2);
		}
	}

}
