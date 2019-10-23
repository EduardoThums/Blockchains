package fabric.api.service.fabric;

import fabric.api.component.chaincode.BaseChaincode;
import fabric.api.component.chaincode.BaseChaincodeFunction;
import fabric.api.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.api.component.chaincode.videoasset.function.CreateVideoAssetFunction;
import fabric.api.component.fabric.ChannelClient;
import fabric.api.model.RecordModel;
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

	public List<String> createTransaction(RecordModel recordModel) throws Exception {
		final String[] arguments = recordModel.toArguments();
		final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		final Collection<ProposalResponse> proposalResponses = channelClient.sendTransactionProposal(baseChaincode);

		return proposalResponses
				.stream()
				.map(ChaincodeResponse::getTransactionID)
				.collect(Collectors.toList());
	}
}
