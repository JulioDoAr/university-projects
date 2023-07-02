package account.decorator;

import account.Account;
import account.AccountType;
import exceptions.NegativeAmountException;
import exceptions.NotEnoughAmountException;
import persistance.service.AccountService;
import persistance.service.AccountServiceImpl;

public abstract class AccountImpl implements Account {

	AccountService accountService;

	protected String code = null;
	protected double amount = 0;
	protected AccountType type;

	protected AccountImpl(String code, double initialDeposit, AccountType type) throws NegativeAmountException {
		this.code = code;
		this.amount = initialDeposit;
		this.type = type;
		accountService = AccountServiceImpl.getInstance();
	}

	@Override
	public double getTotalAmount() {
		return amount + amount * getInterest();
	}

	@Override
	public void depose(double amount) throws NegativeAmountException {
		if (amount < 0)
			throw new NegativeAmountException();
		this.amount += amount;
		accountService.updateAmountByCode(this);
	}

	@Override
	public void retrieve(double amount) throws NegativeAmountException, NotEnoughAmountException {
		if (amount < 0)
			throw new NegativeAmountException();
		if (this.amount < amount)
			throw new NotEnoughAmountException();
		this.amount -= amount;
		accountService.updateAmountByCode(this);
	}

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Account: code=" + code + ", amount=" + amount;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public AccountType getType() {
		return type;
	}
}
