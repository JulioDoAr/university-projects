package persistance.service;

import bank.Bank;

public interface BankService {

	Bank getBankByCode(String code);

	Bank getOrCreate(String code);

}
