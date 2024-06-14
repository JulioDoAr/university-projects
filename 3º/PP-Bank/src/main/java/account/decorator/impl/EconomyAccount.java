package account.decorator.impl;

import account.Account;
import account.decorator.AccountDecorator;

public class EconomyAccount extends AccountDecorator {

	public EconomyAccount(Account decorated) {
		super(decorated);
	}

	@Override
	public String toString() {
		return String.format("%s\nDecorated with Economy Account", decorated.toString());
	}

}
