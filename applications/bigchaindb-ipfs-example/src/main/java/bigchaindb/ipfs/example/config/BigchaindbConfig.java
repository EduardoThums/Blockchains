package bigchaindb.ipfs.example.config;

import bigchaindb.ipfs.example.util.KeyPairLoader;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.model.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eduardo.thums
 */
@Configuration
public class BigchaindbConfig {

	private String peer0Url;

	private String peer1Url;

	private String peer2Url;

	private String peer3Url;

	private KeyPairLoader keyPairLoader;

	public BigchaindbConfig(@Value("${bigchaindb.peer0.url}") String peer0Url,
							@Value("${bigchaindb.peer1.url}") String peer1Url,
							@Value("${bigchaindb.peer2.url}") String peer2Url,
							@Value("${bigchaindb.peer3.url}") String peer3Url,
							KeyPairLoader keyPairLoader) {
		this.peer0Url = peer0Url;
		this.peer1Url = peer1Url;
		this.peer2Url = peer2Url;
		this.peer3Url = peer3Url;
		this.keyPairLoader = keyPairLoader;
	}

	@PostConstruct
	public void createBigchaindbConnection() {
		//TODO: Use multiple peers on production environment
		final List<Connection> connections = Arrays.asList(
				createBaseConnection(peer0Url),
				createBaseConnection(peer1Url),
				createBaseConnection(peer2Url),
				createBaseConnection(peer3Url));

		BigchainDbConfigBuilder
				.baseUrl(peer0Url)
				.setTimeout(60000)
				.setup();
	}

	@PostConstruct
	public void createKeyPair() throws IOException {
		keyPairLoader.createKeyPair();
	}

	private Connection createBaseConnection(String peerUrl) {
		final Map<String, Object> peer01ConnectionConfig = new HashMap<>();
		peer01ConnectionConfig.put("baseUrl", peerUrl);

		return new Connection(peer01ConnectionConfig);
	}
}
