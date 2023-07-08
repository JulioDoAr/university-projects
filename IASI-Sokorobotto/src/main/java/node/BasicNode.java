package node;

import java.util.Set;

import data.Operator;
import data.Position;

/**
 * Nodo basico. Contendra otros nodos basicos
 *
 * @author Julio Dominguez Arjona, Sergio Eslava Velasco
 *
 */
public class BasicNode extends Node<BasicNode> {

	public BasicNode(BasicNode parent, Operator operator, Set<Position> boxes, Position robot) {
		super(parent, operator, boxes, robot);
	}

}
