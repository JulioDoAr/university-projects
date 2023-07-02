package persistance.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import account.AccountType;
import logger.Logger;
import persistance.DatabaseConnection;
import persistance.entity.AccountEntity;

/**
 * 
 * @author Julio
 *
 */
public class AccountRepositoryImpl implements AccountRepository {

	private static final String CREATE = "INSERT INTO 'Account' ('code', 'amount','clientId','accountTypeId') VALUES ('%s', %.2f, %d, %s);";
	private static final String GET_ALL_BY_CLIENT_ID = "SELECT * FROM 'Account' WHERE clientId = %d";
	private static final String UPDATE_AMOUNT = "UPDATE 'Account' SET amount=%.2f WHERE code='%s';";

	/**
	 * Logger
	 */
	private static final Logger log = Logger.getInstance();
	private final Connection conn;

	//
	// SINGLETON
	//
	private static AccountRepository instance;

	private AccountRepositoryImpl() {
		conn = DatabaseConnection.getConnection();
	}

	public static AccountRepository getInstance() {
		if (instance == null)
			instance = new AccountRepositoryImpl();
		return instance;
	}
	//
	// END SINGLETON
	//

	@Override
	public void create(AccountEntity entity) {
		log.write("Executing SQL:");
		log.writeLine(CREATE, entity.getCode(), entity.getAmount(), entity.getClientId(), entity.getAccountTypeId());

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format(Locale.ENGLISH, CREATE, entity.getCode(), entity.getAmount(),
					entity.getClientId(), entity.getAccountTypeId()));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<AccountEntity> getAllByClientId(int id) {
		log.write("Executing SQL:");
		log.writeLine(GET_ALL_BY_CLIENT_ID, id);

		List<AccountEntity> entities = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(GET_ALL_BY_CLIENT_ID, id));
			while (rs.next()) {
				entities.add(resultSetToAccountEntity(rs));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}

	@Override
	public void updateAmountByCode(String code, double amount) {
		log.write("Executing SQL:");
		log.writeLine(UPDATE_AMOUNT, amount, code);

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format(UPDATE_AMOUNT, amount, code));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private AccountEntity resultSetToAccountEntity(ResultSet rs) throws SQLException {
		AccountEntity entity = new AccountEntity();
		entity.setAccountTypeId(AccountType.fromInteger(rs.getInt("accountTypeId")));
		entity.setClientId(rs.getInt("clientId"));
		entity.setAmount(rs.getDouble("amount"));
		entity.setCode(rs.getString("code"));
		entity.setId(rs.getInt("id"));
		return entity;
	}

}
