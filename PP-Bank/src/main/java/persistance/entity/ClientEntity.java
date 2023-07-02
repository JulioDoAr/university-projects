package persistance.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String address;

	@Getter
	@Setter
	private Date birth;

	@Getter
	@Setter
	private int bankId;

}
