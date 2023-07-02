package account.decorator.impl;

import account.Account;
import account.decorator.AccountDecorator;

public class LifeEnsuresAccount extends AccountDecorator {

	public LifeEnsuresAccount(Account decorated) {
		super(decorated);
	}

	@Override
	public String toString() {
		return String.format("%s\nDecorated with Life Ensures Account", decorated.toString());
	}

}
