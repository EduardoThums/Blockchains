package fabric.api.service.fabric;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabric.api.component.chaincode.BaseChaincode;
import fabric.api.component.chaincode.BaseChaincodeFunction;
import fabric.api.component.chaincode.videoasset.VideoAssetChaincode;
import fabric.api.component.chaincode.videoasset.function.QueryByCameraIdAndTimestampRangeFunction;
import fabric.api.component.fabric.ChannelClient;
import fabric.api.exception.InvalidProposalResponseException;
import fabric.api.model.VideoAssetModel;
import fabric.api.service.file.GetFilesByHashListIpfsServiceImpl;
import fabric.api.service.file.GetFilesByHashListService;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private ChannelClient channelClient;

//	private ProduceLogRequestModelService produceLogRequestModelService;

	private GetFilesByHashListService getFilesByHashListService;

	private ObjectMapper objectMapper;

	public QueryByCameraIdAndTimestampRangeService(ChannelClient channelClient, GetFilesByHashListIpfsServiceImpl getFilesByHashListService) {
		this.channelClient = channelClient;
		this.getFilesByHashListService = getFilesByHashListService;
		this.objectMapper = new ObjectMapper();
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(long cameraId, long startDate, long endDate, long logStartDate) throws ProposalException, InvalidArgumentException, IOException {
		final String[] arguments = mapArguments(cameraId, startDate, endDate);
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByCameraIdAndTimestampRangeFunction(arguments);
		final BaseChaincode baseChaincode = new VideoAssetChaincode(baseChaincodeFunction);

		final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
				.stream()
				.findFirst()
				.orElseThrow(InvalidProposalResponseException::new)
				.getMessage());

		final List<VideoAssetModel> videoAssetModels = Arrays.asList(objectMapper.readValue(response, VideoAssetModel[].class));

		final List<String> storageHashList = videoAssetModels
				.stream()
				.map(VideoAssetModel::getStorageHash)
				.collect(Collectors.toList());

		final List<byte[]> videoList = getFilesByHashListService.getFilesByHashList(storageHashList);

		final Long logEndDate = Instant.now().toEpochMilli();

//		produceLogRequestModelService.produceLogRequestModel(logStartDate, logEndDate);

		return videoAssetModels;
	}

	private String[] mapArguments(long cameraId, long startDate, long endDate) {
		return Stream.of(
				String.valueOf(cameraId),
				String.valueOf(startDate),
				String.valueOf(endDate))
				.toArray(String[]::new);
	}
}


