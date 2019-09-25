package fabric.ipfs.example.config;

import fabric.ipfs.example.component.fabric.CaClient;
import fabric.ipfs.example.component.fabric.ChannelClient;
import fabric.ipfs.example.component.fabric.FabricClient;
import fabric.ipfs.example.model.FabricUserModel;
import fabric.ipfs.example.util.DirectoryCleaner;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author eduardo.thums
 */
@Configuration
public class FabricConfig {

	private String channelName;

	private String org1Peer0;

	private String org1Peer0Url;

	private String org1Peer1;

	private String org1Peer1Url;

	private String org1Peer2;

	private String org1Peer2Url;

	private String org1Peer3;

	private String org1Peer3Url;

	private String ordererName;

	private String ordererUrl;

	private CaClient caClient;

	private DirectoryCleaner directoryCleaner;

	public FabricConfig(@Value("${fabric.channel.name}") String channelName,
						@Value("${fabric.org1.peer0.name}") String org1Peer0,
						@Value("${fabric.org1.peer0.url}") String org1Peer0Url,
						@Value("${fabric.org1.peer1.name}") String org1Peer1,
						@Value("${fabric.org1.peer1.url}") String org1Peer1Url,
						@Value("${fabric.org1.peer2.name}") String org1Peer2,
						@Value("${fabric.org1.peer2.url}") String org1Peer2Url,
						@Value("${fabric.org1.peer3.name}") String org1Peer3,
						@Value("${fabric.org1.peer3.url}") String org1Peer3Url,
						@Value("${fabric.orderer.name}") String ordererName,
						@Value("${fabric.orderer.url}") String ordererUrl,
						CaClient caClient,
						DirectoryCleaner directoryCleaner) {
		this.channelName = channelName;
		this.org1Peer0 = org1Peer0;
		this.org1Peer0Url = org1Peer0Url;
		this.org1Peer1 = org1Peer1;
		this.org1Peer1Url = org1Peer1Url;
		this.org1Peer2 = org1Peer2;
		this.org1Peer2Url = org1Peer2Url;
		this.org1Peer3 = org1Peer3;
		this.org1Peer3Url = org1Peer3Url;
		this.ordererName = ordererName;
		this.ordererUrl = ordererUrl;
		this.caClient = caClient;
		this.directoryCleaner = directoryCleaner;
	}

	@Bean
	public ChannelClient channelClient() throws Exception {
		final FabricUserModel adminFabricUser = caClient.registerAdminUser();
		final FabricClient fabricClient = new FabricClient(adminFabricUser);

		final ChannelClient channelClient = fabricClient.createChannelClient(channelName);

		final Channel channel = channelClient.getChannel();
		final Peer peer0 = fabricClient.getInstance().newPeer(org1Peer0, org1Peer0Url);
//		final Peer peer1 = fabricClient.getInstance().newPeer(org1Peer1, org1Peer1Url);
//		final Peer peer2 = fabricClient.getInstance().newPeer(org1Peer2, org1Peer2Url);
//		final Peer peer3 = fabricClient.getInstance().newPeer(org1Peer3, org1Peer3Url);

		final Orderer orderer = fabricClient.getInstance().newOrderer(ordererName, ordererUrl);
		channel.addPeer(peer0);
//		channel.addPeer(peer1);
//		channel.addPeer(peer2);
//		channel.addPeer(peer3);

		channel.addOrderer(orderer);
		channel.initialize();

		return channelClient;
	}
}
