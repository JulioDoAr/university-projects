package account.operation;

import exceptions.NegativeAmountException;
import exceptions.NotEnoughAmountException;

public interface Operations {
	public double getTotalAmount();

	public double getInterest();

	public void depose(double amount) throws NegativeAmountException;

	public void retrieve(double amount) throws NegativeAmountException, NotEnoughAmountException;
}
