package bigchaindb.ipfs.example.service.bigchaindb;

import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.util.KeyPairUtils;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyPair;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BigchaindbServiceImpl implements BigchaindbService {

	@Override
	public List<String> createTransaction(List<String> hashes) throws Exception {
		final KeyPair keyPair = KeyPairUtils.generateNewKeyPair();
		final List<String> transactionsHashes = new ArrayList<>();
		Map<String, String> assetData;

		for (String hash : hashes) {
			assetData = mapAssetData(hash);

			final Transaction transaction = BigchainDbTransactionBuilder
					.init()
					.addAssets(assetData, TreeMap.class)
					.operation(Operations.CREATE)
					.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
					.sendTransaction();

			transactionsHashes.add(transaction.getId());
		}

		return transactionsHashes;
	}

	@Override
	public Transaction findTransactionById(String transactionId) throws IOException {
		return TransactionsApi.getTransactionById(transactionId);
	}

	private Map<String, String> mapAssetData(String hash) {
		return new TreeMap<String, String>() {
			{
				put("hash", hash);
				put("timestampInicio", String.valueOf(Instant.now()));
				put("timestampFim", String.valueOf(Instant.now()));
			}
		};
	}
}
