package chaincode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class VideoAssetChaincode extends ChaincodeBase {

	private final static Logger LOGGER = Logger.getLogger(VideoAssetChaincode.class.getName());

	private ObjectMapper objectMapper;

	private VideoAssetChaincode() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	@Override
	public Response init(ChaincodeStub stub) {
		return ResponseUtils.newSuccessResponse();
	}

	@Override
	public Response invoke(final ChaincodeStub stub) {
		final List<String> params = stub.getParameters();

		switch (stub.getFunction()) {
			case "createVideoAsset":
				try {
					return createVideoAsset(stub, params);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			case "queryByHash":
				return queryByHash(stub, params);
			case "queryByCameraId":
				try {
					return queryByCameraId(stub, params);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			case "queryByCameraIdAndTimestampRange":
				try {
					return queryByCameraIdAndTimestampRange(stub, params);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			default:
				return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
		}
	}

	@Transaction
	private Response createVideoAsset(final ChaincodeStub stub, final List<String> params) throws JsonProcessingException {
		final String key = params.get(2);

		final VideoAsset videoAsset = mapParamsToVideoAsset(params);
		LOGGER.info(videoAsset.getStarDate().toString());

		final String videoAssetState = objectMapper.writeValueAsString(videoAsset);
		LOGGER.info(videoAssetState);

		final VideoAsset deserializedVideoAsset = objectMapper.readValue(videoAssetState, VideoAsset.class);
		LOGGER.info(deserializedVideoAsset.getStarDate().toString());

		stub.putStringState(key, videoAssetState);

		return ResponseUtils.newSuccessResponse(videoAssetState);
	}

	private Response queryByHash(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final String videoAssetState = stub.getStringState(key);

		if (videoAssetState.isEmpty()) {
			return ResponseUtils.newErrorResponse(String.format("Video asset with hash: %s doesn't exit", key));
		}

		return ResponseUtils.newSuccessResponse();
	}

	private Response queryByCameraId(final ChaincodeStub stub, final List<String> params) throws JsonProcessingException {
		final long cameraId = Long.parseLong(params.get(0));
		final String query = "{ \"selector\": { \"cameraId\": " + cameraId + " } }";

		final QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(query);

		final List<String> videoAssetList = new ArrayList<>();
		queryResult.forEach(keyValue -> videoAssetList.add(keyValue.getStringValue()));

		return newSuccessResponse(objectMapper.writeValueAsString(new VideoAssetWrapper(videoAssetList)));
	}

	private Response queryByCameraIdAndTimestampRange(final ChaincodeStub stub, final List<String> params) throws JsonProcessingException {
		final long cameraId = Long.parseLong(params.get(0));
		final Instant startDate = Instant.parse(params.get(1));
		final Instant endDate = Instant.parse(params.get(2));

//		final String query = "{ \"selector\": { \"cameraId\": " + cameraId + ", \"startDate\": { \"$gte\": " + startDate + " }, \"endDate\": { \"$lte\": " + endDate + " } } }";
		final String query = "{ \"selector\": {\"cameraId\": { \"$lte\": " + cameraId + " } } }";
		final QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(query);

		final List<String> videoAssetList = new ArrayList<>();
		queryResult.forEach(keyValue -> videoAssetList.add(keyValue.getStringValue()));

		return newSuccessResponse(objectMapper.writeValueAsString(new VideoAssetWrapper(videoAssetList)));
	}

	private VideoAsset mapParamsToVideoAsset(final List<String> params) {
		final Instant startDate = Instant.parse(params.get(0));
		final Instant endDate = Instant.parse(params.get(1));

		LOGGER.info(startDate.toString());
		LOGGER.info(endDate.toString());

		final String storageHash = params.get(2);
		final String contentHash = params.get(3);
		final long cameraId = Long.parseLong(params.get(4));

		return VideoAsset
				.builder()
				.starDate(startDate)
				.endDate(endDate)
				.storageHash(storageHash)
				.contentHash(contentHash)
				.cameraId(cameraId)
				.build();
	}

	public static void main(String[] args) {
		new VideoAssetChaincode().start(args);
	}
}
