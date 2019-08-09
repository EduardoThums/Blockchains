package basic.network.application.fabric.client;

import basic.network.application.fabric.chaincode.BaseChaincode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class ChannelClient {

	private Channel channel;

	private FabricClient fabricClient;

	public Collection<ProposalResponse> sendTransactionProposal(final BaseChaincode baseChaincode) throws ProposalException, InvalidArgumentException {
		final TransactionProposalRequest request = fabricClient.getInstance().newTransactionProposalRequest();
		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setProposalWaitTime(1000);

		final Collection<ProposalResponse> response = channel.sendTransactionProposal(request, channel.getPeers());
		channel.sendTransaction(response);

		return response;
	}
}
