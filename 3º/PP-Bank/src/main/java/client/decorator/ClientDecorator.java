package client.decorator;

import java.util.Date;
import java.util.Map;

import account.Account;
import account.AccountType;
import client.Client;
import exceptions.AccountNotFoundException;
import exceptions.NegativeAmountException;
import mediatorBankClient.BCMediator;

public class ClientDecorator implements Client {

	protected Client decorated;

	public ClientDecorator(Client decorated) {
		this.decorated = decorated;
	}

	public void addAccount(Account account) {
		decorated.addAccount(account);
	}

	public Account getAccount(String accountCode) throws AccountNotFoundException {
		return decorated.getAccount(accountCode);
	}

	public Map<String, Account> getAccounts() {
		return decorated.getAccounts();
	}

	public String getName() {
		return decorated.getName();
	}

	public void setName(String nume) {
		decorated.setName(nume);
	}

	public String getAddress() {
		return decorated.getAddress();
	}

	public Date getBirth() {
		return decorated.getBirth();
	}

	public void depose(String accountNumber, double amount) throws NegativeAmountException {
		decorated.depose(accountNumber, amount);
	}

	public boolean existAccount(String number) {
		return decorated.existAccount(number);
	}

	public void addAccount(AccountType type) {
		decorated.addAccount(type);
	}

	public void setMediator(BCMediator mediator) {
		decorated.setMediator(mediator);
	}
}
