package persistance.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import account.Account;
import account.factory.AccountEURFactory;
import account.factory.AccountFactory;
import exceptions.NegativeAmountException;
import logger.Logger;
import persistance.entity.AccountEntity;
import persistance.entity.ClientEntity;
import persistance.repository.AccountRepository;
import persistance.repository.AccountRepositoryImpl;
import persistance.repository.ClientRepository;
import persistance.repository.ClientRepositoryImpl;

public class AccountServiceImpl implements AccountService {

	/**
	 * Logger
	 */
	private final Logger log = Logger.getInstance();

	//
	// SINGLETON
	//
	private static AccountService instance;

	private AccountServiceImpl() {
		accountRepository = AccountRepositoryImpl.getInstance();
		clientRepository = ClientRepositoryImpl.getInstance();
	}

	public static AccountService getInstance() {
		if (instance == null)
			instance = new AccountServiceImpl();
		return instance;
	}
	//
	// END SINGLETON
	//

	AccountRepository accountRepository;
	ClientRepository clientRepository;

	@Override
	public void create(Account account, String clientName) {
		ClientEntity clientEntity = clientRepository.findByName(clientName);

		AccountEntity entity = AccountEntity.builder().code(account.getCode()).amount(account.getTotalAmount())
				.clientId(clientEntity.getId()).accountTypeId(account.getType()).build();
		accountRepository.create(entity);
	}

	@Override
	public Map<String, Account> getAllAccountsByClientName(String name) {
		Map<String, Account> map = new TreeMap<>();

		ClientEntity clientEntity = clientRepository.findByName(name);
		if (clientEntity == null)
			return map;

		List<AccountEntity> accounts = accountRepository.getAllByClientId(clientEntity.getId());
		AccountFactory eurFactory = AccountEURFactory.getInstance();
		AccountFactory ronFactory = AccountEURFactory.getInstance();
		try {
			for (AccountEntity entity : accounts) {
				Account aux = null;
				switch (entity.getAccountTypeId()) {
				case EUR:
					aux = eurFactory.build(entity);
					break;
				case RON:
					aux = ronFactory.build(entity);
					break;
				}
				map.put(aux.getCode(), aux);
			}
		} catch (NegativeAmountException e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public void updateAmountByCode(Account account) {
		accountRepository.updateAmountByCode(account.getCode(), account.getAmount());
	}

}
