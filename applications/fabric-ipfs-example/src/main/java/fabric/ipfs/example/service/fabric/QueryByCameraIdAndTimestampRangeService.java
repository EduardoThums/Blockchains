package fabric.ipfs.example.service.fabric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;
import fabric.ipfs.example.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.ipfs.example.component.chaincode.videoasset.function.QueryByCameraIdAndTimestampRangeFunction;
import fabric.ipfs.example.component.fabric.ChannelClient;
import fabric.ipfs.example.controller.fabric.request.QueryByTimestampRequest;
import fabric.ipfs.example.model.VideoAssetModel;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private ChannelClient channelClient;

	private ObjectMapper objectMapper;

	public QueryByCameraIdAndTimestampRangeService(ChannelClient channelClient) {
		this.channelClient = channelClient;
		this.objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(final long cameraId, final QueryByTimestampRequest request) throws ProposalException, InvalidArgumentException, JsonProcessingException {
		final String[] arguments = {String.valueOf(cameraId), String.valueOf(request.getStartDate()), String.valueOf(request.getEndDate())};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByCameraIdAndTimestampRangeFunction(arguments);
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
		final List<VideoAssetModel> videoAssetModels = new ArrayList<>();

		for (String videoAssetSerialized : responseList) {
			videoAssetModels.add(objectMapper.readValue(videoAssetSerialized, VideoAssetModel.class));
		}

		return videoAssetModels;
	}
}
