package fabric.ipfs.example.component.client;

import com.google.gson.Gson;
import fabric.ipfs.example.component.chaincode.BaseChaincode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelClient {

	private Channel channel;

	private FabricClient fabricClient;

	private final static Gson GSON = new Gson();

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

	public Collection<ProposalResponse> queryByChaincode(BaseChaincode baseChaincode) throws InvalidArgumentException, ProposalException {
		final QueryByChaincodeRequest request = fabricClient.getInstance().newQueryProposalRequest();
		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setProposalWaitTime(1000);

		return channel.queryByChaincode(request);
	}
}
