package persistance.entity;

import account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	private double amount;

	@Getter
	@Setter
	private int clientId;

	@Getter
	@Setter
	private AccountType accountTypeId;
}
