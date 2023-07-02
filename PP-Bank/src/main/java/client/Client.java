package client;

import java.util.Date;
import java.util.Map;

import account.Account;
import account.AccountType;
import exceptions.AccountNotFoundException;
import exceptions.NegativeAmountException;
import mediatorBankClient.BCMediator;

public interface Client {

	void addAccount(Account account);

	void addAccount(AccountType type);

	Account getAccount(String accountCode) throws AccountNotFoundException;

	Map<String, Account> getAccounts();

	@Override
	String toString();

	String getName();

	void setName(String name);

	String getAddress();

	Date getBirth();

	void depose(String accountNumber, double amount) throws NegativeAmountException;

	boolean existAccount(String number);

	void setMediator(BCMediator mediator);
}
