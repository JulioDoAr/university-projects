package account.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import account.decorator.AccountImpl;
import account.factory.AccountFactory;
import account.factory.AccountRONFactory;
import exceptions.NegativeAmountException;

public class AccountRONTest {

	AccountRON account;
	private static AccountFactory builder;

	@BeforeClass
	public static void beforeClass() {
		builder = AccountRONFactory.getInstance();
	}

	@Test
	public void test_CreationWithPositiveAmount() throws NegativeAmountException {
		AccountImpl a = builder.build(457875);
		double expectedAmount = 457875 * a.getInterest() + 457875;
		assertEquals(a.getTotalAmount(), expectedAmount, 0.001);
	}

	@Test(expected = NegativeAmountException.class)
	public void test_CreationWithNegativeAmount() throws NegativeAmountException {
		builder.build(-1234);
	}

	@Test
	public void test_CreationWithoutAmount() throws NegativeAmountException {
		AccountImpl a = builder.build();
		double expectedAmount = 0 * a.getInterest();
		assertEquals(a.getTotalAmount(), expectedAmount, 0.001);
	}

	@Test
	public void test_DeposePositiveAmount() throws NegativeAmountException {
		AccountImpl a = builder.build();
		a.depose(100);
		double expectedAmount = 100 * a.getInterest() + 100;
		assertEquals(a.getTotalAmount(), expectedAmount, 0.001);
	}

	@Test(expected = NegativeAmountException.class)
	public void test_DeposeNegativeAmount() throws NegativeAmountException {
		AccountImpl a = builder.build();
		a.depose(-100);
	}

	@Test
	public void test_AccountCodeLenght() throws NegativeAmountException {
		AccountImpl a = builder.build();
		String code = a.getCode();
		assertEquals(code.length(), 6);
	}

	@Test
	public void test_AccountCode_StartsWithRON() throws NegativeAmountException {
		AccountImpl a = builder.build();
		String code = a.getCode();
		assertTrue(code.startsWith("RON"));
	}

	@Test
	public void test_Interest_LessThan500() throws NegativeAmountException {
		AccountImpl a = builder.build();
		assertEquals(a.getInterest(), 0.03, 0.001);
	}

	@Test
	public void test_Interest_500OrMore() throws NegativeAmountException {
		AccountImpl a = builder.build(500);
		assertEquals(a.getInterest(), 0.08, 0.001);
	}
}
