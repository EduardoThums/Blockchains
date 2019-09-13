package fabric.swarm.example.service.fabric;

import fabric.swarm.example.component.chaincode.BaseChaincode;
import fabric.swarm.example.component.chaincode.BaseChaincodeFunction;
import fabric.swarm.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.swarm.example.component.chaincode.videoasset.function.QueryByHashFunction;
import fabric.swarm.example.component.fabric.ChannelClient;
import fabric.swarm.example.exception.TransactionNotFoundException;
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
