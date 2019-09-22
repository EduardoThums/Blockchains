package fabric.ipfs.example.service.fabric;

import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.CreateVideoAssetFunction;
import fabric.ipfs.example.component.chaincode.videoasset.function.QueryByHashFunction;
import fabric.ipfs.example.component.client.CAClient;
import fabric.ipfs.example.component.client.ChannelClient;
import fabric.ipfs.example.component.client.FabricClient;
import fabric.ipfs.example.config.FabricConfig;
import fabric.ipfs.example.model.FabricUserModel;
import fabric.ipfs.example.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FabricServiceImpl implements FabricService {

	private ChannelClient channelClient;

	@Override
	public List<String> createTransaction(String ipfsHash, String hashContent) throws Exception {
		final String[] arguments = {ipfsHash, hashContent};
		final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		final Collection<ProposalResponse> proposalResponses = channelClient.sendTransactionProposal(baseChaincode);

		return proposalResponses
				.stream()
				.map(ChaincodeResponse::getMessage)
				.collect(Collectors.toList());
	}

	@Override
	public String findByHash(String hash) throws ProposalException, InvalidArgumentException {
		final String[] arguments = {hash};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByHashFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		return channelClient.queryByChaincode(baseChaincode)
				.stream()
				.map(ChaincodeResponse::getMessage)
				.findFirst()
				.orElseThrow(RuntimeException::new);
	}

	@PostConstruct
	private void initChannelConfig() throws Exception {
		final Util util = new Util();
		util.cleanUp();

		final CAClient caClient = new CAClient();
		final FabricUserModel adminFabricUser = caClient.registerAdminUser();
		final FabricClient fabricClient = new FabricClient(adminFabricUser);

		this.channelClient = fabricClient.createChannelClient(FabricConfig.CHANNEL_NAME.getValue());

		final Channel channel = channelClient.getChannel();
		final Peer peer0 = fabricClient.getInstance().newPeer(FabricConfig.ORG1_PEER_0.getValue(), FabricConfig.ORG1_PEER_0_URL.getValue());
//		final Peer peer1 = fabricClient.getInstance().newPeer(FabricConfig.ORG1_PEER_1.getValue(), FabricConfig.ORG1_PEER_1_URL.getValue());
//		final Peer peer2 = fabricClient.getInstance().newPeer(FabricConfig.ORG1_PEER_2.getValue(), FabricConfig.ORG1_PEER_2_URL.getValue());
//		final Peer peer3 = fabricClient.getInstance().newPeer(FabricConfig.ORG1_PEER_3.getValue(), FabricConfig.ORG1_PEER_3_URL.getValue());

		final Orderer orderer = fabricClient.getInstance().newOrderer(FabricConfig.ORDERER_NAME.getValue(), FabricConfig.ORDERER_URL.getValue());
		channel.addPeer(peer0);
//		channel.addPeer(peer1);
//		channel.addPeer(peer2);
//		channel.addPeer(peer3);

		channel.addOrderer(orderer);
		channel.initialize();
	}
}
