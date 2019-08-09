package basic.network.application.fabric;

import basic.network.application.config.FabricConfig;
import basic.network.application.fabric.chaincode.BaseChaincode;
import basic.network.application.fabric.chaincode.BaseChaincodeFunction;
import basic.network.application.fabric.chaincode.videoasset.VideoAssetChaincode;
import basic.network.application.fabric.chaincode.videoasset.function.CreateVideoAssetFunction;
import basic.network.application.fabric.client.CAClient;
import basic.network.application.fabric.client.ChannelClient;
import basic.network.application.fabric.client.FabricClient;
import basic.network.application.fabric.user.FabricUser;
import basic.network.application.util.Util;
import org.hyperledger.fabric.sdk.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FabricManager {

	public List<String> insert(final String hash, final String timestamp) {
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
			final ChannelClient channelClient = fabricClient.createChannelClient(FabricConfig.CHANNEL_NAME.getValue());

			// Initialize channel with the respective configs
			final Channel channel = channelClient.getChannel();
			final Peer peer = fabricClient.getInstance().newPeer(FabricConfig.ORG1_PEER_0.getValue(), FabricConfig.ORG1_PEER_0_URL.getValue());
			final EventHub eventHub = fabricClient.getInstance().newEventHub(FabricConfig.EVENT_HUB_NAME.getValue(), FabricConfig.EVENT_HUB_URL.getValue());
			final Orderer orderer = fabricClient.getInstance().newOrderer(FabricConfig.ORDERER_NAME.getValue(), FabricConfig.ORDERER_URL.getValue());
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();

			//VideoAsset chaincode
			final String[] arguments = {hash, timestamp};
			final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);
//			final BaseChaincodeFunction baseChaincodeFunction = new QueryByHashFunction();
			final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

			// Send transaction proposal
			final Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(baseChaincode);
			return responses.stream().map(ChaincodeResponse::getMessage).collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}
}
