package bigchaindb.api.service.bigchaindb;

import bigchaindb.api.model.RecordModel;
import bigchaindb.api.model.VideoAssetModel;
import bigchaindb.api.util.KeyPairLoader;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class CreateTransactionService {

	private KeyPairLoader keyPairLoader;

	public CreateTransactionService(KeyPairLoader keyPairLoader) {
		this.keyPairLoader = keyPairLoader;
	}

	public List<String> createTransaction(RecordModel recordModel) throws Exception {
		List<Transaction> transactions = Collections.singletonList(BigchainDbTransactionBuilder
				.init()
				.addAssets(toVideoAssetModel(recordModel), VideoAssetModel.class)
				.operation(Operations.CREATE)
				.buildAndSign(keyPairLoader.readPublicKey(), keyPairLoader.readPrivateKey())
				.sendTransaction());

		return transactions.stream().map(Transaction::getId).collect(Collectors.toList());
	}

	private VideoAssetModel toVideoAssetModel(RecordModel recordModel) {
		return new VideoAssetModel(
				recordModel.getCameraId(),
				recordModel.getStartDate(),
				recordModel.getEndDate(),
				recordModel.getStorageHash(),
				recordModel.getContentHash());
	}
}
