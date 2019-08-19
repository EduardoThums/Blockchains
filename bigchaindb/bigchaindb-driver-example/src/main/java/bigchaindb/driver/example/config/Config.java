package bigchaindb.driver.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Config {

	BASE_URL("http://localhost:9984"),
	DEFAULT_OUTPUT_AMOUNT("1");

	private String value;
}
