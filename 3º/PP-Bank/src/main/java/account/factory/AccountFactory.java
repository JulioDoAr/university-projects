package account.factory;

import java.util.concurrent.ThreadLocalRandom;

import account.decorator.AccountImpl;
import exceptions.NegativeAmountException;
import persistance.entity.AccountEntity;

public abstract class AccountFactory {

	protected int getRandomNumber() {
		return ThreadLocalRandom.current().nextInt(100, 1000);
	}

	protected abstract String generateAccountNumber();

	public AccountImpl build() throws NegativeAmountException {
		return build(0);
	}

	public abstract AccountImpl build(double initialDeposit) throws NegativeAmountException;

	public abstract AccountImpl build(AccountEntity entity) throws NegativeAmountException;
}
