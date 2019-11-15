package chaincode;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class VideoAssetChaincode extends ChaincodeBase {

	private final static Logger LOGGER = Logger.getLogger(VideoAssetChaincode.class.getName());

	private Gson gson;

	private VideoAssetChaincode() {
		this.gson = new Gson();
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
				return createVideoAsset(stub, params);
			case "queryByHash":
				return queryByHash(stub, params);
			case "queryByCameraId":
				return queryByCameraId(stub, params);
			case "queryByCameraIdAndTimestampRange":
				return queryByCameraIdAndTimestampRange(stub, params);
			default:
				return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
		}
	}

	@Transaction
	private Response createVideoAsset(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final String videoAssetState = gson.toJson(mapParamsToVideoAsset(params));

		stub.putStringState(key, videoAssetState);

		return ResponseUtils.newSuccessResponse(videoAssetState);
	}

	private Response queryByHash(ChaincodeStub stub, List<String> params) {
		final String key = params.get(0);
		final String videoAssetState = stub.getStringState(key);

		if (videoAssetState.isEmpty()) {
			return ResponseUtils.newErrorResponse(String.format("Video asset with hash: %s doesn't exit", key));
		}

		return ResponseUtils.newSuccessResponse(videoAssetState);
	}

	private Response queryByCameraId(final ChaincodeStub stub, final List<String> params) {
		final long cameraId = Long.parseLong(params.get(0));
		final String query = "{ \"selector\": { \"cameraId\": " + cameraId + " } }";

		return newSuccessResponse(getQueryResult(stub, query));
	}

	private Response queryByCameraIdAndTimestampRange(ChaincodeStub stub, List<String> params) {
		final long cameraId = Long.parseLong(params.get(0));
		final long startDate = Long.parseLong(params.get(1));
		final long endDate = Long.parseLong(params.get(2));

		final String query = String.format("{ \"selector\": { \"cameraId\": %d , \"startDate\": { \"$gte\": %d }, \"endDate\": { \"$lte\": %d} }, \"sort\": [\"startDate\"] }",
				cameraId,
				startDate,
				endDate);

		return newSuccessResponse(getQueryResult(stub, query));
	}

	private VideoAsset mapParamsToVideoAsset(List<String> params) {
		final long startDate = Long.parseLong(params.get(1));
		final long endDate = Long.parseLong(params.get(2));
		final String storageHash = params.get(3);
		final String contentHash = params.get(4);
		final long cameraId = Long.parseLong(params.get(5));

		return VideoAsset
				.builder()
				.startDate(startDate)
				.endDate(endDate)
				.storageHash(storageHash)
				.contentHash(contentHash)
				.cameraId(cameraId)
				.build();
	}

	private String getQueryResult(ChaincodeStub stub, String query) {
		final QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(query);

		return StreamSupport.stream(queryResult.spliterator(), false)
				.map(KeyValue::getStringValue)
				.collect(Collectors.toList())
				.toString();
	}

	public static void main(String[] args) {
		new VideoAssetChaincode().start(args);
	}
}
