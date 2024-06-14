package account;

import exceptions.NegativeAmountException;
import exceptions.NotEnoughAmountException;

public interface Account {

	public double getTotalAmount();

	public void depose(double amount) throws NegativeAmountException;

	public void retrieve(double amount) throws NegativeAmountException, NotEnoughAmountException;

	public double getInterest();

	public double getAmount();

	@Override
	public String toString();

	public String getCode();

	public AccountType getType();
}
