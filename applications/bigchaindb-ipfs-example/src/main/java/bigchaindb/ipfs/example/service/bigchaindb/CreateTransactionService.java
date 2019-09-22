package bigchaindb.ipfs.example.service.bigchaindb;

import bigchaindb.ipfs.example.util.KeyPairLoader;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author eduardo.thums
 */
@Service
public class CreateTransactionService {

	private KeyPairLoader keyPairLoader;

	public CreateTransactionService(KeyPairLoader keyPairLoader) {
		this.keyPairLoader = keyPairLoader;
	}

	public List<String> createTransaction(Long cameraId, String storageHash, String contentHash) throws Exception {
		final Map<String, String> assetData = mapAssetData(cameraId, storageHash, contentHash);

		return Collections.singletonList(BigchainDbTransactionBuilder
				.init()
				.addAssets(assetData, TreeMap.class)
				.operation(Operations.CREATE)
				.buildAndSign(keyPairLoader.readPublicKey(), keyPairLoader.readPrivateKey())
				.sendTransaction()
				.getId());
	}

	private Map<String, String> mapAssetData(Long cameraId, String storageHash, String contentHash) {
		return new TreeMap<>() {{
			put("cameraId", cameraId.toString());
			put("storageHash", storageHash);
			put("contentHash", contentHash);
		}};
	}
}
