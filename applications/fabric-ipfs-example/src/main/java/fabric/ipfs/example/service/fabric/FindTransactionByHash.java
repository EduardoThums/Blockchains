package fabric.ipfs.example.service.fabric;

import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.QueryByHashFunction;
import fabric.ipfs.example.component.fabric.ChannelClient;
import fabric.ipfs.example.exception.TransactionNotFoundException;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class FindTransactionByHash {

	private ChannelClient channelClient;

	public FindTransactionByHash(ChannelClient channelClient) {
		this.channelClient = channelClient;
	}

	public String findByHash(String hash) throws ProposalException, InvalidArgumentException {
		final String[] arguments = {hash};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByHashFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		return channelClient.queryByChaincode(baseChaincode)
				.stream()
				.map(ChaincodeResponse::getMessage)
				.findFirst()
				.orElseThrow(() -> new TransactionNotFoundException(hash));
	}
}
