package persistance.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logger.Logger;
import persistance.DatabaseConnection;
import persistance.entity.ClientEntity;

public class ClientRepositoryImpl implements ClientRepository {

	private static final String INSERT = "INSERT INTO 'Client' ('name', 'address', 'bankId', 'birth') VALUES ('%s', '%s',  %d, '%s');";
	private static final String FIND_BY_NAME = "SELECT * FROM 'Client' WHERE name LIKE '%s'";
	private static final String FIND_BY_BANK_ID = "SELECT * FROM 'Client' WHERE bankId = %d";

	/**
	 * Logger
	 */
	private final Logger log;
	private final Connection conn;

	//
	// SINGLETON
	//
	private static ClientRepository instance;

	private ClientRepositoryImpl() {
		log = Logger.getInstance();
		conn = DatabaseConnection.getConnection();
	}

	public static ClientRepository getInstance() {
		if (instance == null)
			instance = new ClientRepositoryImpl();
		return instance;
	}
	//
	// END SINGLETON
	//

	@Override
	public void create(ClientEntity entity) {
		log.write("Executing SQL:");
		log.writeLine(INSERT, entity.getName(), entity.getAddress(), entity.getBankId(), entity.getBirth());

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format(INSERT, entity.getName(), entity.getAddress(), entity.getBankId(),
					entity.getBirth()));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ClientEntity findByName(String name) {
		log.write("Executing SQL:");
		log.writeLine(FIND_BY_NAME, name);

		ClientEntity entity = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(FIND_BY_NAME, name));
			if (rs.next()) {
				entity = resultSetToClientEntity(rs);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public List<ClientEntity> getAllByBankId(int bankId) {
		log.write("Executing SQL:");
		log.writeLine(FIND_BY_BANK_ID, bankId);

		List<ClientEntity> entities = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(FIND_BY_BANK_ID, bankId));
			while (rs.next()) {
				entities.add(resultSetToClientEntity(rs));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}

	private ClientEntity resultSetToClientEntity(ResultSet rs) throws SQLException {
		ClientEntity entity = new ClientEntity();
		entity.setName(rs.getString("name"));
		entity.setAddress(rs.getString("address"));
		entity.setBankId(rs.getInt("bankId"));
		String birth = rs.getString("birth");
		if (birth.equals("null"))
			entity.setBirth(null);
		else
			entity.setBirth(rs.getDate("birth"));

		return entity;
	}

}
