package account.decorator.impl;

import account.Account;
import account.decorator.AccountDecorator;

public class ChildrenAccount extends AccountDecorator {

	public ChildrenAccount(Account decorated) {
		super(decorated);
	}

	@Override
	public String toString() {
		return String.format("%s\nDecorated with Children Account", decorated.toString());
	}

}
