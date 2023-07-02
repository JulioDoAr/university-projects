package mediatorBankClient;

import account.Account;
import account.AccountType;
import bank.Bank;
import client.Client;
import exceptions.NegativeAmountException;
import logger.Logger;

public class BCMediatorImpl implements BCMediator {

	private static final Logger log = Logger.getInstance();

	Bank bank;
	Client client;

	@Override
	public void registerBank(Bank bank) {
		this.bank = bank;

	}

	@Override
	public void registerClient(Client client) {
		this.client = client;
	}

	@Override
	public void addAccount(Account account) {
		if (client.existAccount(account.getCode())) {
			log.write("Client '%s' already have an account with code '%s'", client.getName(), account.getCode());
		} else {
			client.addAccount(account);
		}
	}

	@Override
	public void depose(String account, double deposit) throws NegativeAmountException {
		client.depose(account, deposit);
	}

	@Override
	public void retrieve(String account, double deposit) throws NegativeAmountException {
		client.depose(account, deposit);
	}

	@Override
	public Client getClient() {
		return client;
	}

	@Override
	public void addAccount(AccountType type) {
		bank.addAccount(client.getName(), type);
	}

}
