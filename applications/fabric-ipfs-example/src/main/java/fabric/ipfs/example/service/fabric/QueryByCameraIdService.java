package fabric.ipfs.example.service.fabric;

import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.QueryByCameraIdFunction;
import fabric.ipfs.example.component.fabric.ChannelClient;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdService {


	private ChannelClient channelClient;

	public QueryByCameraIdService(ChannelClient channelClient) {
		this.channelClient = channelClient;
	}

	public String queryByCameraId(Long cameraId) throws ProposalException, InvalidArgumentException {
		final String[] arguments = {cameraId.toString()};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByCameraIdFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		return channelClient.queryByChaincode(baseChaincode)
				.stream()
				.map(ChaincodeResponse::getMessage)
				.findFirst()
				.get();
	}
}
