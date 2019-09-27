package chaincode;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoAssetChaincode extends ChaincodeBase {

	private static final Genson GENSON = new Genson();

	private static final String SERIALIZED_OBJECT_SEPARATOR = "/";

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
			default:
				return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
		}
	}

	@Transaction
	private Response createVideoAsset(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final VideoAsset videoAsset = mapParamsToVideoAsset(params);
		final String videoAssetState = GENSON.serialize(videoAsset);

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

	private Response queryByCameraId(final ChaincodeStub stub, final List<String> params) {
		final long cameraId = Long.parseLong(params.get(0));
		final String query = "{ \"selector\": { \"cameraId\": " + cameraId + " } }";

		final QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(query);

		final List<String> result = new ArrayList<>();
		queryResult.forEach(keyValue -> result.add(keyValue.getStringValue() + SERIALIZED_OBJECT_SEPARATOR));

		return newSuccessResponse(result.toString());
	}

	private VideoAsset mapParamsToVideoAsset(final List<String> params) {
		final String storageHash = params.get(0);
		final String contentHash = params.get(1);
		final long cameraId = Long.parseLong(params.get(2));

		return VideoAsset
				.builder()
				.cameraId(cameraId)
				.storageHash(storageHash)
				.contentHash(contentHash)
				.build();
	}

	public static void main(String[] args) {
		new VideoAssetChaincode().start(args);
	}
}
