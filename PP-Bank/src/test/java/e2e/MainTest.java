package e2e;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import account.AccountType;
import bank.Bank;
import logger.ConsoleOutputOperation;
import logger.Logger;
import persistance.service.BankService;
import persistance.service.BankServiceImpl;

public class MainTest {
	private static Logger log;

	private static BankService bankService;

	@BeforeClass
	public static void before() {
		Locale.setDefault(Locale.ENGLISH);
		log = Logger.getInstance();
		log.reset();
		log.addOutputOperation(new ConsoleOutputOperation());
		bankService = BankServiceImpl.getInstance();
	}

	@After
	public void after() {
		log.finishWrite();
	}

	@Test
	public void test() throws Exception {
		/**
		 * Create BCR bank with 2 clients
		 */
		Bank bcr = bankService.getOrCreate("Banca BCR");

		String ionescu = "Ionescu Ion";
		bcr.addClient(ionescu, "Timisoara");
		String ionescuEurAccount = bcr.addAccount(ionescu, AccountType.EUR, 200.9);
		String ionescuRonAccount = bcr.addAccount(ionescu, AccountType.RON, 400);

		String marinescu = "Marinescu Marin";
		bcr.addClient(marinescu, "Timisoara");
		String marinescuRonAccount = bcr.addAccount(marinescu, AccountType.RON, 200);

		log.writeLine(bcr.toString());

		bcr.deposit(marinescu, 400, marinescuRonAccount);
		log.writeLine(bcr.toString());
		bcr.retrieve(marinescu, 70, marinescuRonAccount);
		log.writeLine(bcr.toString());
		bcr.transfer(marinescu, ionescu, 50.0, marinescuRonAccount, ionescuRonAccount);
		log.writeLine(bcr.toString());

		/**
		 * Create bank CEC with one client
		 */
		Bank cec = bankService.getOrCreate("Banca CEC");

		log.writeLine(cec.toString());

		assertTrue(true);
	}

}
