package bigchaindb.driver.example.service.bigchaindb;

import bigchaindb.driver.example.config.Config;
import bigchaindb.driver.example.controller.bigchaindb.request.CreateTransactionRequest;
import bigchaindb.driver.example.service.keypair.KeyPairService;
import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.FulFill;
import com.bigchaindb.model.Transaction;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BigchaindbServiceImpl implements BigchaindbService {

	private KeyPairService keyPairService;

	public BigchaindbServiceImpl(KeyPairService keyPairService) {
		this.keyPairService = keyPairService;
	}

	public List<Transaction> createTransaction(String publicKeyParam, String privateKeyParam, CreateTransactionRequest request) throws Exception {
		final EdDSAPublicKey publicKey = keyPairService.mapPublicKey(publicKeyParam);
		final EdDSAPrivateKey privateKey = keyPairService.mapPrivateKey(privateKeyParam);
		Map<String, String> assetData;

		final List<Transaction> transactions = new ArrayList<>();

		for (int index = 0; index <= 10; index++) {
			assetData = mapAssetData(request, index);
			final Transaction transaction = BigchainDbTransactionBuilder
					.init()
					.addAssets(assetData, TreeMap.class)
					.operation(Operations.CREATE)
					.buildAndSign(publicKey, privateKey)
					.sendTransaction();

			transactions.add(transaction);
		}

		return transactions;
	}

	public Transaction transferTransaction(String transactionID, String ownerPublicKeyParam, String ownerPrivateKeyParam, String newOwnerPublicKeyParam) throws Exception {
		final FulFill fulFill = new FulFill();
		fulFill.setTransactionId(transactionID);
		fulFill.setOutputIndex(0);

		final EdDSAPublicKey ownerPublicKey = keyPairService.mapPublicKey(ownerPublicKeyParam);
		final EdDSAPrivateKey ownerPrivateKey = keyPairService.mapPrivateKey(ownerPrivateKeyParam);
		final EdDSAPublicKey newOwnerPublicKey = keyPairService.mapPublicKey(newOwnerPublicKeyParam);

		return BigchainDbTransactionBuilder
				.init()
				.addInput(null, fulFill, ownerPublicKey)
				.addOutput(Config.DEFAULT_OUTPUT_AMOUNT.getValue(), newOwnerPublicKey)
				.addAssets(transactionID, String.class)
				.operation(Operations.TRANSFER)
				.buildAndSign(ownerPublicKey, ownerPrivateKey)
				.sendTransaction();
	}

	@Override
	public List<Transaction> findTransactionByID(List<String> transactionIds) throws IOException {
		final List<Transaction> transactions = new ArrayList<>();

		for (String transactionId : transactionIds) {
			final Transaction transaction = TransactionsApi.getTransactionById(transactionId);
			transactions.add(transaction);
		}

		return transactions;
	}

	private Map<String, String> mapAssetData(CreateTransactionRequest request, int index) {
		return new TreeMap<String, String>() {
			{
				put("hash", request.getHash() + index);
				put("timestamp", String.valueOf(Instant.now()));
			}
		};
	}
}
