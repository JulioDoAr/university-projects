package persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankEntity {

	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String name;
}
