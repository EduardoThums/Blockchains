package bigchaindb.ipfs.example.config;

import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.model.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eduardo.thums
 */
@Component
public class BigchainDbConfig {

	@Value("${spring.bigchaindb.peer0Address}")
	private String peer0Address;

	@Value("${spring.bigchaindb.peer1Address}")
	private String peer1Address;

	@Value("${spring.bigchaindb.peer2Address}")
	private String peer2Address;

	@Value("${spring.bigchaindb.peer3Address}")
	private String peer3Address;

	@PostConstruct
	public void initBigchainDbConfig() {
		final Map<String, Object> peer01ConnectionConfig = new HashMap<>();
		peer01ConnectionConfig.put("baseUrl", peer0Address);

		final Map<String, Object> peer02ConnectionConfig = new HashMap<>();
		peer02ConnectionConfig.put("baseUrl", peer1Address);

		final Map<String, Object> peer03ConnectionConfig = new HashMap<>();
		peer03ConnectionConfig.put("baseUrl", peer2Address);

		final Map<String, Object> peer04ConnectionConfig = new HashMap<>();
		peer04ConnectionConfig.put("baseUrl", peer3Address);

		final Connection peer0Connection = new Connection(peer01ConnectionConfig);
		final Connection peer1Connection = new Connection(peer02ConnectionConfig);
		final Connection peer2Connection = new Connection(peer03ConnectionConfig);
		final Connection peer3Connection = new Connection(peer04ConnectionConfig);

		final List<Connection> connections = Arrays.asList(
				peer0Connection,
				peer1Connection,
				peer2Connection,
				peer3Connection);

		BigchainDbConfigBuilder
				.baseUrl(peer0Address)
//                .addConnections(connections)
				.setTimeout(60000)
				.setup();
	}
}
