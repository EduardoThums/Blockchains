package fabric.ipfs.example.service.fabric;

import com.owlike.genson.Genson;
import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.QueryByCameraIdFunction;
import fabric.ipfs.example.component.fabric.ChannelClient;
import fabric.ipfs.example.model.VideoAssetModel;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdService {

	private Genson genson;

	private ChannelClient channelClient;

	public QueryByCameraIdService(ChannelClient channelClient) {
		this.channelClient = channelClient;
		this.genson = new Genson();
	}

	public List<VideoAssetModel> queryByCameraId(Long cameraId) throws ProposalException, InvalidArgumentException {
		final String[] arguments = {cameraId.toString()};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByCameraIdFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		//MODO GAMBETINHA
		final String response = channelClient.queryByChaincode(baseChaincode)
				.stream()
				.map(ChaincodeResponse::getMessage)
				.findFirst()
				.get()
				.replace("[", "")
				.replace("]", "");

		final List<String> responseList = new ArrayList<>(Arrays.asList(response.split("/")));
		return responseList
				.stream()
				.map(videoAssetString -> genson.deserialize(videoAssetString, VideoAssetModel.class))
				.collect(Collectors.toList());
	}
}
