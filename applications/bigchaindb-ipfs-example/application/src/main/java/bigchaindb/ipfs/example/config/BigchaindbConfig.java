package bigchaindb.ipfs.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BigchaindbConfig {

	//TODO: Change this configs to application.yml
	BASE_URL("http://localhost:9984"),

	DEFAULT_OUTPUT_AMOUNT("1");

	private String value;
}
