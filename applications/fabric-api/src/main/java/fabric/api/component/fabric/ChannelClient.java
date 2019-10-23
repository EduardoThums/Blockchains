package fabric.api.component.fabric;

import fabric.api.component.chaincode.BaseChaincode;
import lombok.Getter;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author eduardo.thums
 */
@Getter
@Component
public class ChannelClient {

	private static final String CHAINCODE_VERSION = "3.0";

	private Channel channel;

	private FabricClient fabricClient;

	ChannelClient(Channel channel, FabricClient fabricClient) {
		this.channel = channel;
		this.fabricClient = fabricClient;
	}

	public Collection<ProposalResponse> sendTransactionProposal(BaseChaincode baseChaincode) throws ProposalException, InvalidArgumentException {
		final TransactionProposalRequest request = fabricClient.getInstance().newTransactionProposalRequest();
		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.setVersion(CHAINCODE_VERSION)
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setProposalWaitTime(2000);

		final Collection<ProposalResponse> response = channel.sendTransactionProposal(request, channel.getPeers());
		channel.sendTransaction(response);

		return response;
	}

	public Collection<ProposalResponse> queryByChaincode(BaseChaincode baseChaincode) throws InvalidArgumentException, ProposalException {
		final QueryByChaincodeRequest request = fabricClient.getInstance().newQueryProposalRequest();
		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.setVersion(CHAINCODE_VERSION)
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setProposalWaitTime(1000);

		return channel.queryByChaincode(request);
	}
}
