package org.hyperledger.fabric.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
@AllArgsConstructor
public class ChannelClient {

	private Channel channel;

	private FabricClient fabricClient;

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);

	private static final String EXPECTED_EVENT_NAME = "event";

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
//
//	public Collection<ProposalResponse> queryByChainCode(final BaseChaincode baseChaincode) throws InvalidArgumentException, ProposalException {
//
//
//		final Collection<ProposalResponse> response = channel.queryByChaincode(request);
//
//		for (final ProposalResponse proposalResponse : response) {
//			System.out.printf("Transaction proposal on channel %s %s %s with transaction id: %s\n",
//					channel.getName(),
//					proposalResponse.getMessage(),
//					proposalResponse.getStatus().toString(),
//					proposalResponse.getTransactionID());
//		}
//
//		return response;
//	}
}
