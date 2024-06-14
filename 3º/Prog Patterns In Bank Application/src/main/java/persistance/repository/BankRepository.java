package persistance.repository;

import persistance.entity.BankEntity;

public interface BankRepository {

	BankEntity getBankByCode(String code);

	void create(BankEntity entity);

}
