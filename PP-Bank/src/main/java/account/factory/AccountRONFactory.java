package account.factory;

import java.util.Set;
import java.util.TreeSet;

import account.AccountType;
import account.decorator.AccountImpl;
import account.types.AccountRON;
import exceptions.NegativeAmountException;
import persistance.entity.AccountEntity;

/**
 * Singleton
 * 
 * @author Julio Domínguez Arjona
 *
 */
public class AccountRONFactory extends AccountFactory {

	private static AccountFactory instance = null;

	public synchronized static AccountFactory getInstance() {
		if (instance == null)
			instance = new AccountRONFactory();
		return instance;
	}

	private Set<Integer> generatedNumbers;

	private AccountRONFactory() {
		generatedNumbers = new TreeSet<Integer>();
	}

	@Override
	protected String generateAccountNumber() {
		int number;
		do {
			number = getRandomNumber();
		} while (generatedNumbers.contains(number));
		generatedNumbers.add(number);

		return "RON" + number;
	}

	@Override
	public AccountImpl build() throws NegativeAmountException {
		return build(0);
	}

	@Override
	public AccountImpl build(double initialDeposit) throws NegativeAmountException {
		return new AccountRON(generateAccountNumber(), initialDeposit, AccountType.RON);
	}

	@Override
	public AccountImpl build(AccountEntity entity) throws NegativeAmountException {
		return new AccountRON(entity.getCode(), entity.getAmount(), AccountType.RON);
	}

}
