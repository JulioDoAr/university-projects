package persistance.repository;

import java.util.List;

import persistance.entity.AccountEntity;

public interface AccountRepository {

	void create(AccountEntity entity);

	List<AccountEntity> getAllByClientId(int id);

	void updateAmountByCode(String code, double amount);

}
