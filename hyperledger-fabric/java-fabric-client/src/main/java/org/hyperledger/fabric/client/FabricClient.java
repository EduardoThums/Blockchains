package org.hyperledger.fabric.client;

import lombok.Getter;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class FabricClient {

	private HFClient instance;

	public FabricClient(final User user) throws CryptoException, InvalidArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		instance = HFClient.createNewInstance();
		instance.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
		instance.setUserContext(user);
	}

	public ChannelClient createChannelClient(final String channelName) throws InvalidArgumentException {
		final Channel channel = instance.newChannel(channelName);
		return new ChannelClient(channel, this);
	}


	public Collection<ProposalResponse> deployChainCode(
			String chainCodeName,
			String chaincodePath,
			String codepath,
			String language,
			String version,
			Collection<Peer> peers)
			throws InvalidArgumentException, IOException, ProposalException {
		InstallProposalRequest request = instance.newInstallProposalRequest();
		ChaincodeID.Builder chaincodeIDBuilder = ChaincodeID.newBuilder().setName(chainCodeName).setVersion(version)
				.setPath(chaincodePath);
		ChaincodeID chaincodeID = chaincodeIDBuilder.build();
		Logger.getLogger(FabricClient.class.getName()).log(Level.INFO,
				"Deploying chaincode " + chainCodeName + " using Fabric client " + instance.getUserContext().getMspId()
						+ " " + instance.getUserContext().getName());
		request.setChaincodeID(chaincodeID);
		request.setUserContext(instance.getUserContext());
		request.setChaincodeSourceLocation(new File(codepath));
		request.setChaincodeVersion(version);
		Collection<ProposalResponse> responses = instance.sendInstallProposal(request, peers);
		return responses;
	}
}
