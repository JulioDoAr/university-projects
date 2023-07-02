package account.command.impl;

import account.Account;
import account.command.Command;
import exceptions.NegativeAmountException;

public class AccountDeposeCommand implements Command {

	private Account account;
	private double amount;

	public AccountDeposeCommand(Account account, double amount) {
		this.account = account;
		this.amount = amount;
	}

	public void execute() throws NegativeAmountException {
		account.depose(amount);
	}

}
