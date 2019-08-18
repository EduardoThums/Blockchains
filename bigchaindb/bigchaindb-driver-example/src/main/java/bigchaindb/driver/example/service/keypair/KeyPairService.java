package bigchaindb.driver.example.service.keypair;

import bigchaindb.driver.example.controller.keypair.response.KeyPairResponse;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

import java.security.spec.InvalidKeySpecException;

public interface KeyPairService {

	KeyPairResponse createKeyPair() throws InvalidKeySpecException;

	EdDSAPublicKey mapPublicKey(String bytePublicKey) throws InvalidKeySpecException;

	EdDSAPrivateKey mapPrivateKey(String bytePrivateKey) throws InvalidKeySpecException;
}
