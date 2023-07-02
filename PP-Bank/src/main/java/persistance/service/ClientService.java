package persistance.service;

import java.util.List;

import bank.Bank;
import client.Client;

public interface ClientService {

	void create(Client client, Bank bank);

	Client findByName(String name);

	List<Client> getAllByBankId(String code);

}
