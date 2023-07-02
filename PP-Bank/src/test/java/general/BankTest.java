package general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import account.Account;
import account.AccountType;
import bank.Bank;
import exceptions.ClientNotFoundException;
import exceptions.NegativeAmountException;

public class BankTest {

	private Bank b;

	@Before
	public void before() {
		b = new Bank("Test");
	}

	@Test
	public void test_addClienteAndGetIt() throws NegativeAmountException, ClientNotFoundException {
		b.addClient("Name", "Address");
		assertNotNull(b.getClient("Name"));
	}

	@Test(expected = ClientNotFoundException.class)
	public void test_getClientThatNotExist() throws NegativeAmountException, ClientNotFoundException {
		b.addClient("Name", "Address");
		b.getClient("NotExist");
	}

	@Test
	public void test_addAccountToClient() throws NegativeAmountException, ClientNotFoundException {
		b.addClient("Name", "Address");
		b.addAccount("Name", AccountType.EUR);
		assertEquals(b.getClient("Name").getAccounts().size(), 1);
	}

	@Test
	public void test_addAccountWithMoneyToClient() throws NegativeAmountException, ClientNotFoundException {
		b.addClient("Name", "Address");
		b.addAccount("Name", AccountType.EUR, 150);
		Account a = b.getClient("Name").getAccounts().values().iterator().next();
		double expectedAmount = 150 * a.getInterest() + 150;
		assertEquals(a.getTotalAmount(), 150 + 150 * a.getInterest(), 0.001);
	}

}
