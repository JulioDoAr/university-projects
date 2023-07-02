package account.decorator;

import account.Account;
import account.AccountType;
import exceptions.NegativeAmountException;
import exceptions.NotEnoughAmountException;

public abstract class AccountDecorator implements Account {

	protected Account decorated;

	public AccountDecorator(Account decorated) {
		this.decorated = decorated;
	}

	@Override
	public double getTotalAmount() {
		return decorated.getTotalAmount();
	}

	@Override
	public void depose(double amount) throws NegativeAmountException {
		decorated.depose(amount);
	}

	@Override
	public void retrieve(double amount) throws NegativeAmountException, NotEnoughAmountException {
		decorated.retrieve(amount);
	}

	@Override
	public double getInterest() {
		return decorated.getInterest();
	}

	@Override
	public String toString() {
		return decorated.toString();
	}

	@Override
	public String getCode() {
		return decorated.getCode();
	}

	@Override
	public double getAmount() {
		return decorated.getAmount();
	}

	@Override
	public AccountType getType() {
		return decorated.getType();
	}

}
