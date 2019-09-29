package fabric.ipfs.example.service.fabric;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.QueryByCameraIdFunction;
import fabric.ipfs.example.component.fabric.ChannelClient;
import fabric.ipfs.example.model.VideoAssetModel;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdService {

	private ChannelClient channelClient;

	private ObjectMapper objectMapper;

	public QueryByCameraIdService(ChannelClient channelClient) {
		this.channelClient = channelClient;
		this.objectMapper = new ObjectMapper();
		this.objectMapper.findAndRegisterModules();
	}

	public List<VideoAssetModel> queryByCameraId(Long cameraId) throws ProposalException, InvalidArgumentException, IOException, ClassNotFoundException {
		final String[] arguments = {cameraId.toString()};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByCameraIdFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
				.stream()
				.findFirst()
				.orElse(null))
				.getMessage();

		final VideoAssetModel[] videoAssetModels = objectMapper.readValue(response, VideoAssetModel[].class);

		return Arrays.asList(videoAssetModels);
	}
}
