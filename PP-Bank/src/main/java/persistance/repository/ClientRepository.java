package persistance.repository;

import java.util.List;

import persistance.entity.ClientEntity;

public interface ClientRepository {

	void create(ClientEntity clientEntity);

	ClientEntity findByName(String name);

	List<ClientEntity> getAllByBankId(int bankId);

}
