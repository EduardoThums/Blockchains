package org.hyperledger.fabric.service;

import com.google.gson.Gson;
import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;
import org.hyperledger.fabric.chaincode.videoasset.VideoAssetChaincode;
import org.hyperledger.fabric.chaincode.videoasset.function.CreateVideoAssetFunction;
import org.hyperledger.fabric.chaincode.videoasset.function.QueryByHashFunction;
import org.hyperledger.fabric.chaincode.videoasset.function.QueryByTimestampRangeFunction;
import org.hyperledger.fabric.client.CAClient;
import org.hyperledger.fabric.client.ChannelClient;
import org.hyperledger.fabric.client.FabricClient;
import org.hyperledger.fabric.config.Config;
import org.hyperledger.fabric.model.VideoAssetModel;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.user.FabricUser;
import org.hyperledger.fabric.util.Util;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabricServiceImpl implements FabricService {

	private Gson gson;

	public FabricServiceImpl(Gson gson) {
		this.gson = gson;
	}

	private String callChaincode(final BaseChaincode baseChaincode) {
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

			final Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(baseChaincode);

			return String.valueOf(responses.stream().map(ChaincodeResponse::getMessage).findFirst());
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new RuntimeException();
	}

	@Override
	public VideoAssetModel createVideoAsset(String hash, Integer year) {
		final String[] arguments = {hash};
		final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);

		final String chaincodeResult = callChaincode(new VideoAssetChaincode(baseChaincodeFunction));

		return gson.fromJson(chaincodeResult, VideoAssetModel.class);
	}

	@Override
	public VideoAssetModel queryByHash(String hash) {
		final String[] arguments = {hash};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByHashFunction(arguments);

		final String chaincodeResult = callChaincode(new VideoAssetChaincode(baseChaincodeFunction));

		return gson.fromJson(chaincodeResult, VideoAssetModel.class);
	}

	@Override
	public List<VideoAssetModel> queryByYearRange(Integer year) {
		final String[] arguments = {String.valueOf(year)};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByTimestampRangeFunction(arguments);

		final List<String> chaincodeResult = Collections.singletonList(callChaincode(new VideoAssetChaincode(baseChaincodeFunction)));


		return chaincodeResult
				.stream()
				.map(videoAssetState ->
						gson.fromJson(videoAssetState, VideoAssetModel.class))
				.collect(Collectors.toList());
	}
}
