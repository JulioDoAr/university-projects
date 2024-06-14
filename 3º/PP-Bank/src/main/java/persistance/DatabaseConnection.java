package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import logger.Logger;

public class DatabaseConnection {

	private static final String URL = "jdbc:sqlite:database.db";
	private static final Logger log = Logger.getInstance();

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null)
			initializeConnection();
		return conn;
	}

	private static void initializeConnection() {
		try {
			conn = DriverManager.getConnection(URL);
			log.writeLine("Connection to SQLite has been established.");
			createTablesIfNotExist();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createTablesIfNotExist() {
		execute(Query.DROP_ACCOUNT);
		execute(Query.DROP_CLIENT);
		execute(Query.DROP_BANK);
		execute(Query.CREATE_BANK);
		execute(Query.CREATE_CLIENT);
		execute(Query.CREATE_ACCOUNT);
	}

//	private void initializeData() {
//		log.writeLine("Initializing data...");
//		execute(Query.DROP_ACCOUNT);
//		execute(Query.DROP_ACCOUNTTYPE);
//		execute(Query.DROP_CLIENT);
//		execute(Query.DROP_BANK);
//
//		execute(Query.CREATE_BANK);
//		execute(Query.POPULATE_BANK);
//
//		execute(Query.CREATE_CLIENT);
//		execute(Query.POPULATE_CLIENT);
//
//		execute(Query.CREATE_ACCOUNTTYPE);
//		execute(Query.POPULATE_ACCOUNTTYPE);
//
//		execute(Query.CREATE_ACCOUNT);
//		execute(Query.POPULATE_ACCOUNT);
//		log.writeLine("Data initialization finished");
//	}

	private static void execute(String query) {
		log.writeLine("Executing... %s", query);
		try {
			Statement stmt = conn.createStatement();
			if (query.startsWith("DROP"))
				stmt.executeQuery(query);
			if (query.startsWith("CREATE"))
				stmt.executeQuery(query);
			else if (query.startsWith("INSERT"))
				stmt.executeUpdate(query);
			stmt.close();
			conn.commit();
			log.writeLine("Correct execution!!");
		} catch (SQLException e) {
			log.writeLine(e.getMessage());
		}
	}
}
