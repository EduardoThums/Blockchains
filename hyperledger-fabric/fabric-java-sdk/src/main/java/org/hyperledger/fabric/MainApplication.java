package org.hyperledger.fabric;

import org.hyperledger.fabric.client.CAClient;
import org.hyperledger.fabric.client.ChannelClient;
import org.hyperledger.fabric.client.FabricClient;
import org.hyperledger.fabric.config.Config;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.user.UserContext;
import org.hyperledger.fabric.util.Util;

import java.util.Collection;

public class MainApplication {

	public static void main(String[] args) {
		try {
			// Clean configs before any event
			Util.cleanUp();

			// Create CAClient
			final CAClient caClient = new CAClient(Config.CA_ORG1_URL, null);

			// Register and enroll admin user to Org1 MSP
			final UserContext adminUserContext = caClient.registerAdminUser();

			// Create fabric client
			final FabricClient fabClient = new FabricClient(adminUserContext);

			// Create channel client
			final ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);

			// Initialize channel with the respective configs
			final Channel channel = channelClient.getChannel();
			final Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			final EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			final Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();

			// Create a transaction proposal
			final TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			request.setChaincodeID(ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build());
			request.setFcn(Config.CREATE_CAR_FUNCTION_NAME);
			request.setArgs(Config.CREATE_CAR_FUNCTION_ARGUMENTS);
			request.setProposalWaitTime(1000);

			// Send transaction proposal
			final Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
			responses.forEach(proposalResponse ->
					System.out.printf("Invoked createCar on %s. Status - %s", Config.CHAINCODE_1_NAME, proposalResponse.getStatus())
			);
		} catch (
				Exception e) {
			e.printStackTrace();
		}
	}
}

