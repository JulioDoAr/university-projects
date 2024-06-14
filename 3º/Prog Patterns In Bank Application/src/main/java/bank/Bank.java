package bank;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import account.Account;
import account.AccountType;
import account.factory.AccountEURFactory;
import account.factory.AccountRONFactory;
import client.Client;
import client.ClientBuilder;
import exceptions.AccountNotFoundException;
import exceptions.ClientNotFoundException;
import exceptions.NegativeAmountException;
import logger.Logger;
import mediatorBankClient.BCMediator;
import mediatorBankClient.BCMediatorImpl;
import persistance.service.AccountService;
import persistance.service.AccountServiceImpl;
import persistance.service.ClientService;
import persistance.service.ClientServiceImpl;

public class Bank {

	private final Logger log = Logger.getInstance();
	private Map<String, BCMediator> mediators = new TreeMap<String, BCMediator>();
	private ClientService clientService;
	private AccountService accountService;
	private String code;

	public Bank(String code) {
		this.code = code;
		clientService = ClientServiceImpl.getInstance();
		accountService = AccountServiceImpl.getInstance();

		List<Client> clients = clientService.getAllByBankId(code);
		for (Client client : clients)
			addClient(client);
	}

	public void addClient(String name, String address) {
		addClient(name, address, null);
	}

	public void addClient(String name, String address, Date birth) {
		if (mediators.containsKey(name)) {
			log.write("Already exist a client with name '%s'", name);
		} else {
			Client client = new ClientBuilder().setName(name).setAddress(address).setBirth(birth).build();
			clientService.create(client, this);
			addClient(client);
		}
	}

	public void addClient(Client client) {
		BCMediator mediator = new BCMediatorImpl();
		mediator.registerBank(this);
		mediator.registerClient(client);
		client.setMediator(mediator);
		mediators.put(client.getName(), mediator);
		log.writeLine("Client added. %s", client.toString());
	}

	public Client getClient(String name) throws ClientNotFoundException {
		BCMediator med = mediators.get(name);
		if (med != null)
			return med.getClient();
		else
			throw new ClientNotFoundException();
	}

	public void addAccount(String clientName, AccountType type) {
		addAccount(clientName, type, 0);
	}

	public String addAccount(String clientName, AccountType type, double initialDeposit) {
		Account account = null;
		BCMediator mediator = mediators.get(clientName);
		try {
			switch (type) {
			case EUR:
				account = AccountEURFactory.getInstance().build(initialDeposit);
				break;
			case RON:
				account = AccountRONFactory.getInstance().build(initialDeposit);
				break;
			}
			log.write("Account with number %s added to %s.", account.getCode(), clientName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mediator.addAccount(account);
		return account.getCode();
	}

	public void deposit(String client, double deposit, String account) throws ClientNotFoundException {
		BCMediator mediator = mediators.get(client);
		try {
			mediator.depose(account, deposit);
		} catch (NegativeAmountException e) {
			e.printStackTrace();
		}
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Bank [code=");
		sb.append(code);
		sb.append(", clients=");
		sb.append(mediators.keySet());
		sb.append("]");
		return sb.toString();
	}

	public void retrieve(String client, double amount, String account) throws NegativeAmountException {
		BCMediator mediator = mediators.get(client);
		mediator.retrieve(account, amount);
	}

	public void transfer(String origin, String destination, double amount, String originAccount,
			String destinationAccount) throws AccountNotFoundException, NegativeAmountException {
		BCMediator originMediator = mediators.get(origin);
		Client originClient = originMediator.getClient();
		if (!originClient.existAccount(originAccount))
			throw new AccountNotFoundException();

		BCMediator destinationMediator = mediators.get(destination);
		Client destinationClient = mediators.get(destination).getClient();
		if (!destinationClient.existAccount(destinationAccount))
			throw new AccountNotFoundException();

		originMediator.retrieve(originAccount, amount);
		destinationMediator.depose(destinationAccount, amount);
	}

}
