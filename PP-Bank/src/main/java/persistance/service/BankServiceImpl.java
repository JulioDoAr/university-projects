package persistance.service;

import bank.Bank;
import logger.Logger;
import persistance.entity.BankEntity;
import persistance.repository.BankRepository;
import persistance.repository.BankRepositoryImpl;

public class BankServiceImpl implements BankService {

	/**
	 * Logger
	 */
	private final Logger log = Logger.getInstance();

	//
	// SINGLETON
	//
	private static BankService instance;

	private BankServiceImpl() {
		repository = BankRepositoryImpl.getInstance();
	}

	public static BankService getInstance() {
		if (instance == null)
			instance = new BankServiceImpl();
		return instance;
	}
	//
	// END SINGLETON
	//

	private BankRepository repository;

	@Override
	public Bank getBankByCode(String code) {
		BankEntity entity = repository.getBankByCode(code);
		if (entity != null)
			return new Bank(entity.getName());
		return null;
	}

	@Override
	public Bank getOrCreate(String code) {
		Bank bank = null;
		BankEntity entity = repository.getBankByCode(code);
		if (entity == null) {
			log.writeLine("%s bank does not exist. It will be created.", code);
			entity = BankEntity.builder().name(code).build();
			repository.create(entity);

			bank = new Bank(code);
		} else {
			bank = new Bank(entity.getName());
		}

		return bank;

	}

}
