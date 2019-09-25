package fabric.ipfs.example.service.fabric;

import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.CreateVideoAssetFunction;
import fabric.ipfs.example.component.fabric.ChannelClient;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class CreateTransactionService {

	private ChannelClient channelClient;

	public CreateTransactionService(ChannelClient channelClient) {
		this.channelClient = channelClient;
	}

	public List<String> createTransaction(Long cameraId, String storageHash, String contentHash) throws Exception {
		final String[] arguments = {storageHash, contentHash, cameraId.toString()};
		final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		final Collection<ProposalResponse> proposalResponses = channelClient.sendTransactionProposal(baseChaincode);

		return proposalResponses
				.stream()
				.map(ChaincodeResponse::getTransactionID)
				.collect(Collectors.toList());
	}
}
