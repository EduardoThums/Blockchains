package bigchaindb.api.service.bigchaindb;

import bigchaindb.api.model.RecordModel;
import bigchaindb.api.model.VideoAssetModel;
import bigchaindb.api.util.KeyPairLoader;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
		return Collections.singletonList(BigchainDbTransactionBuilder
				.init()
//				.addAssetDataClass(VideoAssetModel.class, new VideoAssetDeserializer())
				.addAssets(toVideoAssetModel(recordModel), VideoAssetModel.class)
				.operation(Operations.CREATE)
				.buildAndSign(keyPairLoader.readPublicKey(), keyPairLoader.readPrivateKey())
				.sendTransaction()
				.getId());
	}

	private VideoAssetModel toVideoAssetModel(RecordModel recordModel) {
		return new VideoAssetModel(
				recordModel.getStartDate(),
				recordModel.getEndDate(),
				recordModel.getStorageHash(),
				recordModel.getContentHash(),
				recordModel.getCameraId());
	}
}
