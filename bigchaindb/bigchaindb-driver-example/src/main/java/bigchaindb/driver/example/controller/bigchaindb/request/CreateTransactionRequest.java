package bigchaindb.driver.example.controller.bigchaindb.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

	private String name;

	private String author;

	private String place;

	private String year;
}
