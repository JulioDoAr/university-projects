package data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PositionTest {

	@Test
	public void test_equal() {
		Position p1 = new Position(2, 1);
		Position p2 = new Position(2, 1);
		Position p3 = new Position(1, 2);

		assertTrue(p1.equals(p2));
		assertFalse(p1.equals(p3));

	}
}
