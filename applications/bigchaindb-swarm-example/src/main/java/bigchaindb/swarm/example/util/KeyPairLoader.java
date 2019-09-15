package bigchaindb.swarm.example.util;

import com.bigchaindb.util.KeyPairUtils;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Objects;

/**
 * @author eduardo.thums
 */
@Component
public class KeyPairLoader {

	private String keyPairDirectoryPath;

	private String privateKeyFileName;

	public KeyPairLoader(@Value("${bigchaindb.keyPair.directoryPath}") String keyPairDirectoryPath,
						 @Value("${bigchaindb.keyPair.privateKeyFileName}") String privateKeyFileName) {
		this.keyPairDirectoryPath = keyPairDirectoryPath;
		this.privateKeyFileName = privateKeyFileName;
	}

	public void createKeyPair() throws IOException {
		final File keyPairDirectory = new File(keyPairDirectoryPath);

		if (!keyPairDirectory.exists()) {
			keyPairDirectory.mkdirs();
		}

		final KeyPair keyPair = KeyPairUtils.generateNewKeyPair();
		createPrivateKeyFile(keyPair.getPrivate());
	}

	public EdDSAPublicKey readPublicKey() throws IOException {
		return (EdDSAPublicKey) Objects.requireNonNull(readKeyPair()).getPublic();
	}

	public EdDSAPrivateKey readPrivateKey() throws IOException {
		return (EdDSAPrivateKey) Objects.requireNonNull(readKeyPair()).getPrivate();
	}

	private KeyPair readKeyPair() throws IOException {
		final String publicKeyPath = keyPairDirectoryPath + "/" + privateKeyFileName;
		final File publicKeyFile = new File(publicKeyPath);

		if (!publicKeyFile.exists()) {
			return null;
		}

		final byte[] bytes = Files.readAllBytes(publicKeyFile.toPath());

		return KeyPairUtils.decodeKeyPair(bytes);
	}

	private void createPrivateKeyFile(PrivateKey privateKey) throws IOException {
		final String publicKeyPath = keyPairDirectoryPath + "/" + privateKeyFileName;

		final FileOutputStream fileOutputStream = new FileOutputStream(publicKeyPath);
		fileOutputStream.write(privateKey.getEncoded());
		fileOutputStream.close();
	}
}
