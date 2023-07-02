package persistance;

public class Query {

	public static final String DROP_ACCOUNT = "DROP Table IF EXISTS 'Account';";
	public static final String DROP_ACCOUNTTYPE = "DROP Table IF EXISTS 'AccountType';";
	public static final String DROP_CLIENT = "DROP Table IF EXISTS 'Client';";
	public static final String DROP_BANK = "DROP Table IF EXISTS 'Bank';";
	public static final String CREATE_ACCOUNT = "CREATE TABLE 'Account' ('id' INTEGER,'code' TEXT NOT NULL,'amount' REAL NOT NULL DEFAULT 0,'clientId' INTEGER NOT NULL,'accountTypeId' INTEGER NOT NULL,PRIMARY KEY ('id'),CONSTRAINT 'Account_Client' FOREIGN KEY ('clientId') REFERENCES 'Client' ('id') ON UPDATE NO ACTION ON DELETE CASCADE);";
	public static final String CREATE_ACCOUNTTYPE = "CREATE TABLE 'AccountType' ('id' INTEGER,'name' TEXT NOT NULL,PRIMARY KEY ('id'));";
	public static final String CREATE_CLIENT = "CREATE Table 'Client' ('id' INTEGER,'name' TEXT NOT NULL,'address' TEXT NOT NULL,'birth' DATE NULL DEFAULT NULL,'bankId' INTEGER NOT NULL,PRIMARY KEY ('id'),CONSTRAINT 'Client_Bank' FOREIGN KEY ('bankId') REFERENCES 'Bank' ('id') ON UPDATE NO ACTION ON DELETE CASCADE);";
	public static final String CREATE_BANK = "CREATE Table 'Bank' ('id' INTEGER, 'name' TEXT NOT NULL, PRIMARY KEY ('id'));";
	public static final String POPULATE_ACCOUNT = "INSERT INTO 'Account' ('code', 'amount','clientId','accountTypeId') VALUES ('123', 200, 1, 1);INSERT INTO 'Account' ('code', 'amount','clientId','accountTypeId') VALUES ('123', 400, 1, 2);INSERT INTO 'Account' ('code', 'amount','clientId','accountTypeId') VALUES ('123', 200, 2, 2);INSERT INTO 'Account' ('code', 'amount','clientId','accountTypeId') VALUES ('123', 700, 3, 1);";
	public static final String POPULATE_ACCOUNTTYPE = "INSERT INTO 'AccountType' ('name') VALUES ('EUR');INSERT INTO 'AccountType' ('name') VALUES ('RON');";
	public static final String POPULATE_CLIENT = "INSERT INTO 'Client' ('name', 'address', 'bankId') VALUES ('Ionescu Ion', 'Timisoara', 1);INSERT INTO 'Client' ('name', 'address', 'bankId') VALUES ('Marinescu Marin', 'Timisoara', 1);INSERT INTO 'Client' ('name', 'address', 'bankId') VALUES ('Vasilescu Vasile', 'Brasov', 2);";
	public static final String POPULATE_BANK = "INSERT INTO 'Bank' ('name') VALUES ('Banca BCR');INSERT INTO 'Bank' ('name') VALUES ('Banca BCR');";
}
