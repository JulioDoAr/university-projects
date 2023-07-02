package account.command.impl;

import account.Account;
import account.command.Command;
import exceptions.NegativeAmountException;
import exceptions.NotEnoughAmountException;

public class AccountTransferCommand implements Command {

	private Account origin, destination;
	private float amount;

	public AccountTransferCommand(Account origin, Account destination, float amount) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.amount = amount;
	}

	public void execute() throws NegativeAmountException, NotEnoughAmountException {
		origin.retrieve(amount);
		destination.depose(amount);
	}

}
