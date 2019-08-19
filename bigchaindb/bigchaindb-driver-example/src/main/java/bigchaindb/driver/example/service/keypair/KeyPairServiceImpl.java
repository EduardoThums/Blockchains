package bigchaindb.driver.example.service.keypair;

import bigchaindb.driver.example.controller.keypair.response.KeyPairResponse;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyPairServiceImpl implements KeyPairService {

	//TODO: Use bigchaindb KeyPairUtils class

	@Override
	public KeyPairResponse createKeyPair() {
		final KeyPairGenerator keyPairGenerator = new KeyPairGenerator();
		final KeyPair keyPair = keyPairGenerator.generateKeyPair();

		return KeyPairResponse
				.builder()
				.publicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()))
				.privateKey(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()))
				.build();
	}

	@Override
	public EdDSAPublicKey mapPublicKey(String publicKey) throws InvalidKeySpecException {
		final byte[] bytePublicKey = Base64.getDecoder().decode(publicKey);

		return new EdDSAPublicKey(new X509EncodedKeySpec(bytePublicKey));
	}

	@Override
	public EdDSAPrivateKey mapPrivateKey(String bytePrivateKey) throws InvalidKeySpecException {
		final byte[] bytePublicKey = Base64.getDecoder().decode(bytePrivateKey);

		return new EdDSAPrivateKey(new PKCS8EncodedKeySpec(bytePublicKey));
	}
}
