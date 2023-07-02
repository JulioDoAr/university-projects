package account.command.impl;

import account.command.Command;
import account.decorator.AccountImpl;
import exceptions.NegativeAmountException;
import exceptions.NotEnoughAmountException;

public class AccountRetrieveCommand implements Command {

	private AccountImpl account;
	private float amount;

	public AccountRetrieveCommand(AccountImpl account, float amount) {
		this.account = account;
		this.amount = amount;
	}

	public void execute() throws NegativeAmountException, NotEnoughAmountException {
		account.retrieve(amount);
	}

}
