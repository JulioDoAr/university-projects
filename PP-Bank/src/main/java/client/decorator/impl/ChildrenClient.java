package client.decorator.impl;

import client.Client;
import client.decorator.ClientDecorator;

public class ChildrenClient extends ClientDecorator {

	public ChildrenClient(Client decorated) {
		super(decorated);
	}

	@Override
	public String toString() {
		return String.format("%s\nDecorated with Children Client", decorated.toString());
	}
}
