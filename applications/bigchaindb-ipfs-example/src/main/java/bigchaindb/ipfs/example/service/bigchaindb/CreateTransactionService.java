package bigchaindb.ipfs.example.service.bigchaindb;

import bigchaindb.ipfs.example.model.VideoAssetModel;
import bigchaindb.ipfs.example.util.KeyPairLoader;
import bigchaindb.ipfs.example.util.VideoAssetDeserializer;
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

	public List<String> createTransaction(VideoAssetModel videoAssetModel) throws Exception {
		return Collections.singletonList(BigchainDbTransactionBuilder
				.init()
				.addAssetDataClass(VideoAssetModel.class, new VideoAssetDeserializer())
				.addAssets(videoAssetModel, VideoAssetModel.class)
				.operation(Operations.CREATE)
				.buildAndSign(keyPairLoader.readPublicKey(), keyPairLoader.readPrivateKey())
				.sendTransaction()
				.getId());
	}
}
