package bigchaindb.driver.example.service.bigchaindb;

import bigchaindb.driver.example.controller.bigchaindb.request.CreateTransactionRequest;
import bigchaindb.driver.example.service.keypair.KeyPairService;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.FulFill;
import com.bigchaindb.model.Transaction;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class BigchaindbServiceImpl implements BigchaindbService {

	private final static String DEFAULT_OUTPUT_AMOUNT = "1";

	private KeyPairService keyPairService;

	public BigchaindbServiceImpl(KeyPairService keyPairService) {
		this.keyPairService = keyPairService;
		BigchainDbConfigBuilder.baseUrl("http://localhost:9984").setup();
	}

	public Transaction createTransaction(String publicKeyParam, String privateKeyParam, CreateTransactionRequest request) throws Exception {
		final EdDSAPublicKey publicKey = keyPairService.mapPublicKey(publicKeyParam);
		final EdDSAPrivateKey privateKey = keyPairService.mapPrivateKey(privateKeyParam);
		final Map<String, String> assetData = mapAssetData(request);

		return BigchainDbTransactionBuilder
				.init()
				.addAssets(assetData, TreeMap.class)
				.operation(Operations.CREATE)
				.buildAndSign(publicKey, privateKey)
				.sendTransaction();
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
				.addOutput(DEFAULT_OUTPUT_AMOUNT, newOwnerPublicKey)
				.addAssets(transactionID, String.class)
				.operation(Operations.TRANSFER)
				.buildAndSign(ownerPublicKey, ownerPrivateKey)
				.sendTransaction();
	}

	private Map<String, String> mapAssetData(CreateTransactionRequest request) {
		return new TreeMap<String, String>() {{
			put("name", request.getName());
			put("author	", request.getAuthor());
			put("place", request.getPlace());
			put("year", request.getYear());
		}};
	}
}
