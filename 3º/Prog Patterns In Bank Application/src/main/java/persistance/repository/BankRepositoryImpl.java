package persistance.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logger.Logger;
import persistance.DatabaseConnection;
import persistance.entity.BankEntity;

public class BankRepositoryImpl implements BankRepository {

	private static final String GET_BANK_BY_CODE = "select * from bank where name like '%s'";
	private static final String CREATE = "INSERT INTO 'Bank' ('name') VALUES ('%s');";

	/**
	 * Logger
	 */
	private final Logger log = Logger.getInstance();
	private final Connection conn;

	//
	// SINGLETON
	//
	private static BankRepository instance;

	private BankRepositoryImpl() {
		conn = DatabaseConnection.getConnection();
	}

	public static BankRepository getInstance() {
		if (instance == null)
			instance = new BankRepositoryImpl();
		return instance;
	}
	//
	// END SINGLETON
	//

	@Override
	public BankEntity getBankByCode(String code) {
		log.write("Executing SQL:");
		log.writeLine(GET_BANK_BY_CODE, code);

		BankEntity entity = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(GET_BANK_BY_CODE, code));
			if (rs.next()) {
				entity = new BankEntity();
				entity.setName(rs.getString("name"));
				entity.setId(rs.getInt("id"));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public void create(BankEntity entity) {
		log.write("Executing SQL:");
		log.writeLine(CREATE, entity.getName());

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format(CREATE, entity.getName()));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
