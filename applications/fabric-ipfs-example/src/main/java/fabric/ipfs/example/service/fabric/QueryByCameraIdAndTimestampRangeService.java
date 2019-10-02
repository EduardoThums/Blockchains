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
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private ChannelClient channelClient;

	private ObjectMapper objectMapper;

	public QueryByCameraIdAndTimestampRangeService(ChannelClient channelClient) {
		this.channelClient = channelClient;
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(long cameraId, QueryByTimestampRequest request) throws ProposalException, InvalidArgumentException, JsonProcessingException {
		final String[] arguments = getFunctionArguments(cameraId, request.getStartDate(), request.getEndDate());
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByCameraIdAndTimestampRangeFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
				.stream()
				.findFirst()
				.orElse(null))
				.getMessage();

		final VideoAssetModel[] videoAssetModels = objectMapper.readValue(response, VideoAssetModel[].class);

		return Arrays.asList(videoAssetModels);
	}

	private String[] getFunctionArguments(long cameraId, Instant startDate, Instant endDate) {
		return Stream.of(
				String.valueOf(cameraId),
				String.valueOf(startDate.getEpochSecond()),
				String.valueOf(endDate.getEpochSecond()))
				.toArray(String[]::new);
	}
}
