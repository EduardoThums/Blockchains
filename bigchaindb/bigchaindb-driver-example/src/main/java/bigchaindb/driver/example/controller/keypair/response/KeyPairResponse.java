package bigchaindb.driver.example.controller.keypair.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KeyPairResponse {

	private String publicKey;

	private String privateKey;
}
