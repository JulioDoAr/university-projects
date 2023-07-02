package exceptions;

public class NegativeAmountException extends Exception {

	public NegativeAmountException() {
		super("Deposits can not be a negative value.");
	}

}
