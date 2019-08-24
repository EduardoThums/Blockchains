package org.hyperledger.fabric.service.fabric;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;
import org.hyperledger.fabric.chaincode.videoasset.VideoAssetChaincode;
import org.hyperledger.fabric.chaincode.videoasset.function.CreateVideoAssetFunction;
import org.hyperledger.fabric.chaincode.videoasset.function.QueryByHashFunction;
import org.hyperledger.fabric.client.CAClient;
import org.hyperledger.fabric.client.ChannelClient;
import org.hyperledger.fabric.client.FabricClient;
import org.hyperledger.fabric.config.Config;
import org.hyperledger.fabric.model.VideoAssetModel;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.user.FabricUser;
import org.hyperledger.fabric.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Slf4j
@Service
public class FabricServiceImpl implements FabricService {

	private final static Gson GSON = new Gson();

	private ChannelClient channelClient;

	@Override
	public VideoAssetModel createVideoAsset(String hash) {
		final String[] arguments = {hash, hash};
		final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);

		final String chaincodeResult = callChaincode(new VideoAssetChaincode(baseChaincodeFunction));

//		Optional.of(chaincodeResult).get();
		return GSON.fromJson(chaincodeResult, VideoAssetModel.class);
	}

	@Override
	public VideoAssetModel queryByHash(String hash) {
		final String[] arguments = {hash};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByHashFunction(arguments);

		final String chaincodeResult = callChaincode(new VideoAssetChaincode(baseChaincodeFunction));

		return GSON.fromJson(chaincodeResult, VideoAssetModel.class);
	}

	private String callChaincode(final BaseChaincode baseChaincode) {
		try {
//			// Clean configs before any event
//			final Util util = new Util();
//			util.cleanUp();
//
//			// Create CAClient
//			final CAClient caClient = new CAClient();
//
//			// Register and enroll admin user to Org1 MSP
//			final FabricUser adminFabricUser = caClient.registerAdminUser();
//
//			// Create fabric client
//			final FabricClient fabricClient = new FabricClient(adminFabricUser);
//
//			// Create channel client
//			final ChannelClient channelClient = fabricClient.createChannelClient(Config.CHANNEL_NAME.getValue());
//
//			// Initialize channel with the respective configs
//			final Channel channel = channelClient.getChannel();
//			final Peer peer = fabricClient.getInstance().newPeer(Config.ORG1_PEER_0.getValue(), Config.ORG1_PEER_0_URL.getValue());
//			final EventHub eventHub = fabricClient.getInstance().newEventHub(Config.EVENT_HUB_NAME.getValue(), Config.EVENT_HUB_URL.getValue());
//			final Orderer orderer = fabricClient.getInstance().newOrderer(Config.ORDERER_NAME.getValue(), Config.ORDERER_URL.getValue());
//			channel.addPeer(peer);
//			channel.addEventHub(eventHub);
//			channel.addOrderer(orderer);
//			channel.initialize();

			final Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(baseChaincode);

			//TODO: throw custom exception
			return responses.stream().map(ChaincodeResponse::getMessage).findFirst().orElseThrow(RuntimeException::new);
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new RuntimeException();
	}

//	@PostConstruct
//	private void initChannelConfig() throws Exception {
//		final Util util = new Util();
//		util.cleanUp();
//
//		final CAClient caClient = new CAClient();
//		final FabricUser adminFabricUser = caClient.registerAdminUser();
//		final FabricClient fabricClient = new FabricClient(adminFabricUser);
//
//		this.channelClient = fabricClient.createChannelClient(Config.CHANNEL_NAME.getValue());
//
//		// Initialize channel with the respective configs
//		final Channel channel = channelClient.getChannel();
//		final Peer peer = fabricClient.getInstance().newPeer(Config.ORG1_PEER_0.getValue(), Config.ORG1_PEER_0_URL.getValue());
//		final EventHub eventHub = fabricClient.getInstance().newEventHub(Config.EVENT_HUB_NAME.getValue(), Config.EVENT_HUB_URL.getValue());
//		final Orderer orderer = fabricClient.getInstance().newOrderer(Config.ORDERER_NAME.getValue(), Config.ORDERER_URL.getValue());
//		channel.addPeer(peer);
//		channel.addEventHub(eventHub);
//		channel.addOrderer(orderer);
//		channel.initialize();
//	}

	@PostConstruct
	private void initChannelConfig() throws Exception {
		final Util util = new Util();
		util.cleanUp();

		final CAClient caClient = new CAClient();
		final FabricUser adminFabricUser = caClient.registerAdminUser();
		final FabricClient fabricClient = new FabricClient(adminFabricUser);

		this.channelClient = fabricClient.createChannelClient(Config.CHANNEL_NAME.getValue());

		final Channel channel = channelClient.getChannel();
		final Peer peer = fabricClient.getInstance().newPeer(Config.ORG1_PEER_0.getValue(), Config.ORG1_PEER_0_URL.getValue());
		final EventHub eventHub = fabricClient.getInstance().newEventHub(Config.EVENT_HUB_NAME.getValue(), Config.EVENT_HUB_URL.getValue());
		final Orderer orderer = fabricClient.getInstance().newOrderer(Config.ORDERER_NAME.getValue(), Config.ORDERER_URL.getValue());
		channel.addPeer(peer);
		channel.addEventHub(eventHub);
		channel.addOrderer(orderer);
		channel.initialize();
	}
}
