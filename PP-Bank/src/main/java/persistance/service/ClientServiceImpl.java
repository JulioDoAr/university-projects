package persistance.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import bank.Bank;
import client.Client;
import client.ClientBuilder;
import logger.Logger;
import persistance.entity.BankEntity;
import persistance.entity.ClientEntity;
import persistance.repository.BankRepository;
import persistance.repository.BankRepositoryImpl;
import persistance.repository.ClientRepository;
import persistance.repository.ClientRepositoryImpl;

public class ClientServiceImpl implements ClientService {

	/**
	 * Logger
	 */
	private final Logger log = Logger.getInstance();

	//
	// SINGLETON
	//
	private static ClientService instance;

	private ClientServiceImpl() {
		clientRepository = ClientRepositoryImpl.getInstance();
		bankRepository = BankRepositoryImpl.getInstance();
	}

	public static ClientService getInstance() {
		if (instance == null)
			instance = new ClientServiceImpl();
		return instance;
	}
	//
	// END SINGLETON
	//

	ClientRepository clientRepository;
	BankRepository bankRepository;

	@Override
	public void create(Client client, Bank bank) {

		BankEntity bankEntity = bankRepository.getBankByCode(bank.getCode());

		Date birth = null;
		if (client.getBirth() != null)
			birth = new Date(client.getBirth().getTime());

		ClientEntity clientEntity = ClientEntity.builder().name(client.getName()).address(client.getAddress())
				.birth(birth).bankId(bankEntity.getId()).build();
		clientRepository.create(clientEntity);
	}

	@Override
	public Client findByName(String name) {
		ClientEntity entity = clientRepository.findByName(name);

		return entityToClient(entity);
	}

	@Override
	public List<Client> getAllByBankId(String code) {
		BankEntity bank = bankRepository.getBankByCode(code);
		return clientRepository.getAllByBankId(bank.getId()).stream().map(ClientServiceImpl::entityToClient)
				.collect(Collectors.toList());
	}

	private static Client entityToClient(ClientEntity entity) {
		return new ClientBuilder().setName(entity.getName()).setAddress(entity.getAddress()).setBirth(entity.getBirth())
				.build();
	}

}
