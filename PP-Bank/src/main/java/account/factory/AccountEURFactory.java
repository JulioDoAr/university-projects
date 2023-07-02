package account.factory;

import java.util.Set;
import java.util.TreeSet;

import account.AccountType;
import account.decorator.AccountImpl;
import account.types.AccountEUR;
import exceptions.NegativeAmountException;
import persistance.entity.AccountEntity;

/**
 * Singleton
 * 
 * @author Julio Domínguez Arjona
 *
 */
public class AccountEURFactory extends AccountFactory {

	private static AccountFactory instance = null;

	public synchronized static AccountFactory getInstance() {
		if (instance == null)
			instance = new AccountEURFactory();
		return instance;
	}

	private Set<Integer> generatedNumbers;

	private AccountEURFactory() {
		generatedNumbers = new TreeSet<Integer>();
	}

	@Override
	protected String generateAccountNumber() {
		int number;
		do {
			number = getRandomNumber();
		} while (generatedNumbers.contains(number));
		generatedNumbers.add(number);

		return "EUR" + number;
	}

	@Override
	public AccountImpl build() throws NegativeAmountException {
		return build(0);
	}

	@Override
	public AccountImpl build(double initialDeposit) throws NegativeAmountException {
		return new AccountEUR(generateAccountNumber(), initialDeposit, AccountType.EUR);
	}

	@Override
	public AccountImpl build(AccountEntity entity) throws NegativeAmountException {
		return new AccountEUR(entity.getCode(), entity.getAmount(), AccountType.EUR);
	}

}
