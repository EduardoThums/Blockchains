package org.hyperledger.fabric;

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.chaincode.fabcar.FabCarChaincode;
import org.hyperledger.fabric.chaincode.fabcar.function.CreateCarFunction;
import org.hyperledger.fabric.client.CAClient;
import org.hyperledger.fabric.client.ChannelClient;
import org.hyperledger.fabric.client.FabricClient;
import org.hyperledger.fabric.config.Config;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.user.FabricUser;
import org.hyperledger.fabric.util.Util;

import java.util.Collection;

@Slf4j
public class MainApplication {
	public static void main(String[] args) {
		try {
			// Clean configs before any event
			final Util util = new Util();
			util.cleanUp();

			// Create CAClient
			final CAClient caClient = new CAClient();

			// Register and enroll admin user to Org1 MSP
			final FabricUser adminFabricUser = caClient.registerAdminUser();

			// Create fabric client
			final FabricClient fabricClient = new FabricClient(adminFabricUser);

			// Create channel client
			final ChannelClient channelClient = fabricClient.createChannelClient(Config.CHANNEL_NAME.getValue());

			// Initialize channel with the respective configs
			final Channel channel = channelClient.getChannel();
			final Peer peer = fabricClient.getInstance().newPeer(Config.ORG1_PEER_0.getValue(), Config.ORG1_PEER_0_URL.getValue());
			final EventHub eventHub = fabricClient.getInstance().newEventHub(Config.EVENT_HUB_NAME.getValue(), Config.EVENT_HUB_URL.getValue());
			final Orderer orderer = fabricClient.getInstance().newOrderer(Config.ORDERER_NAME.getValue(), Config.ORDERER_URL.getValue());
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();

			//Create car chaincode
			final CreateCarFunction createCarFunction = new CreateCarFunction();
			final BaseChaincode fabCarChaincode = new FabCarChaincode(createCarFunction);

			// Send transaction proposal
			final Collection<ProposalResponse> responses = channelClient.queryByChainCode(fabCarChaincode);
			responses.forEach(proposalResponse -> log.info(String.valueOf(proposalResponse.getStatus())));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
