package account;

public enum AccountType {
	EUR(1), RON(2);

	private int id;

	private AccountType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static AccountType fromInteger(int x) {
		switch (x) {
		case 1:
			return EUR;
		case 2:
			return RON;
		}
		return null;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
}
