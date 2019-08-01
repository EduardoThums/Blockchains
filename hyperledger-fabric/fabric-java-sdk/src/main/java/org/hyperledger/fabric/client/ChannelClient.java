package org.hyperledger.fabric.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@AllArgsConstructor
public class ChannelClient {

	private String name;

	private Channel channel;

	private FabricClient fabClient;

	public Collection<ProposalResponse> sendTransactionProposal(final TransactionProposalRequest request) throws ProposalException, InvalidArgumentException {
		System.out.printf("Sending transaction proposal on channel %s", channel.getName());

		final Collection<ProposalResponse> response = channel.sendTransactionProposal(request, channel.getPeers());
		for (ProposalResponse proposalResponse : response) {
			final String stringResponse = new String(proposalResponse.getChaincodeActionResponsePayload());

			System.out.printf("Transaction proposal on channel %s %s %s with transaction id: %s\n",
					channel.getName(),
					proposalResponse.getMessage(),
					proposalResponse.getStatus().toString(),
					proposalResponse.getTransactionID());
			System.out.printf("%s\n", stringResponse);
		}

		final CompletableFuture<TransactionEvent> transactionEventCompletableFuture = channel.sendTransaction(response);

		System.out.printf("%s\n", transactionEventCompletableFuture.toString());

		return response;
	}

	public Collection<ProposalResponse> queryByChainCode(String chaincodeName, String functionName, String[] args)
			throws InvalidArgumentException, ProposalException {
		Logger.getLogger(ChannelClient.class.getName()).log(Level.INFO,
				"Querying " + functionName + " on channel " + channel.getName());
		QueryByChaincodeRequest request = fabClient.getInstance().newQueryProposalRequest();
		ChaincodeID ccid = ChaincodeID.newBuilder().setName(chaincodeName).build();
		request.setChaincodeID(ccid);
		request.setFcn(functionName);
		if (args != null)
			request.setArgs(args);

		Collection<ProposalResponse> response = channel.queryByChaincode(request);

		return response;
	}
}
