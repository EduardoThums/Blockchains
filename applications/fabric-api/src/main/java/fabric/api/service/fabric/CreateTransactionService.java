package fabric.api.service.fabric;

import fabric.api.component.chaincode.BaseChaincode;
import fabric.api.component.chaincode.BaseChaincodeFunction;
import fabric.api.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.api.component.chaincode.videoasset.function.CreateVideoAssetFunction;
import fabric.api.component.fabric.ChannelClient;
import fabric.api.model.RecordModel;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Slf4j
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

		log.info("Camera ID: {}", recordModel.getCameraId());
		log.info("Start date: {}", recordModel.getStartDate());
		log.info("End date: {}", recordModel.getEndDate());

		final Collection<ProposalResponse> proposalResponses = channelClient.sendTransactionProposal(baseChaincode);

		return proposalResponses
				.stream()
				.map(ChaincodeResponse::getTransactionID)
				.collect(Collectors.toList());
	}
}
