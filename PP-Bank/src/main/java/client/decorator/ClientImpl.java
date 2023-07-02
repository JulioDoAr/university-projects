package client.decorator;

import java.util.Date;
import java.util.Map;

import account.Account;
import account.AccountType;
import account.command.impl.AccountDeposeCommand;
import client.Client;
import exceptions.NegativeAmountException;
import mediatorBankClient.BCMediator;
import persistance.service.AccountService;
import persistance.service.AccountServiceImpl;

public class ClientImpl implements Client {

	private String name;
	private String address;
	private Map<String, Account> accounts;
	private Date birth;
	private BCMediator mediator;
	private AccountService accountService;

	public ClientImpl(BCMediator mediator, String name, String address) {
		this(mediator, name, address, null);
	}

	public ClientImpl(BCMediator mediator, String name, String address, Date birth) {
		this.name = name;
		this.address = address;
		this.birth = birth;
		this.mediator = mediator;
		this.accountService = AccountServiceImpl.getInstance();

		accounts = accountService.getAllAccountsByClientName(name);
	}

	@Override
	public void addAccount(Account account) {
		accounts.put(account.getCode(), account);
		accountService.create(account, name);
	}

	@Override
	public void addAccount(AccountType type) {
		mediator.addAccount(type);
	}

	@Override
	public Account getAccount(String accountCode) {
		return accounts.get(accountCode);

	}

	@Override
	public Map<String, Account> getAccounts() {
		return accounts;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\tClient [name=");
		sb.append(name);
		sb.append(", address=");
		sb.append(address);
		sb.append(", acounts=");
		sb.append(accounts);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String nume) {
		this.name = nume;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public Date getBirth() {
		return birth;
	}

	@Override
	public void depose(String accountNumber, double amount) throws NegativeAmountException {
		Account account = getAccount(accountNumber);
		AccountDeposeCommand command = new AccountDeposeCommand(account, amount);
		command.execute();
	}

	@Override
	public boolean existAccount(String number) {
		return accounts.containsKey(number);
	}

	@Override
	public void setMediator(BCMediator mediator) {
		this.mediator = mediator;
	}

}