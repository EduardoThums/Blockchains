package bigchaindb.driver.example.controller.keypair;

import bigchaindb.driver.example.controller.keypair.response.KeyPairResponse;
import bigchaindb.driver.example.service.keypair.KeyPairService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/key-pair")
public class KeyPairController {

	private KeyPairService keyPairService;

	public KeyPairController(KeyPairService keyPairService) {
		this.keyPairService = keyPairService;
	}

	@PostMapping("/create")
	public ResponseEntity<KeyPairResponse> create() throws InvalidKeySpecException, NoSuchAlgorithmException {
		return ResponseEntity.ok(keyPairService.createKeyPair());
	}
}
