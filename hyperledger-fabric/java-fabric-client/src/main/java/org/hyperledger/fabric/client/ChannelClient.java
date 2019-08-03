package org.hyperledger.fabric.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public class ChannelClient {

	private Channel channel;

	private FabricClient fabricClient;

	public Collection<ProposalResponse> sendTransactionProposal(final TransactionProposalRequest request) throws ProposalException, InvalidArgumentException {
		final Collection<ProposalResponse> response = channel.sendTransactionProposal(request, channel.getPeers());
		for (final ProposalResponse proposalResponse : response) {
			final String stringResponse = new String(proposalResponse.getChaincodeActionResponsePayload());

			System.out.printf("Transaction proposal on channel %s %s %s with transaction id: %s\n",
					channel.getName(),
					proposalResponse.getMessage(),
					proposalResponse.getStatus().toString(),
					proposalResponse.getTransactionID());
			System.out.printf("%s\n", stringResponse);
		}

		final CompletableFuture<BlockEvent.TransactionEvent> transactionEventCompletableFuture = channel.sendTransaction(response);

		return response;
	}

	public Collection<ProposalResponse> queryByChainCode(BaseChaincode baseChaincode) throws InvalidArgumentException, ProposalException {
		final QueryByChaincodeRequest request = fabricClient.getInstance().newQueryProposalRequest();
		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.setVersion(baseChaincode.getVersion())
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setProposalWaitTime(1000);

		final Collection<ProposalResponse> response = channel.queryByChaincode(request);

		for (final ProposalResponse proposalResponse : response) {
			System.out.printf("Transaction proposal on channel %s %s %s with transaction id: %s\n",
					channel.getName(),
					proposalResponse.getMessage(),
					proposalResponse.getStatus().toString(),
					proposalResponse.getTransactionID());
		}

		return response;
	}
}
