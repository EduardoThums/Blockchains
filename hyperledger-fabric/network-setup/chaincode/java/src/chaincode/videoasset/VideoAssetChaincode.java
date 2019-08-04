package chaincode.videoasset;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.List;

public class VideoAssetChaincode extends ChaincodeBase {

	private static final Genson GENSON = new Genson();

	@Override
	public Response init(ChaincodeStub stub) {
		return newSuccessResponse();
	}

	@Override
	public Response invoke(final ChaincodeStub stub) {
		final List<String> params = stub.getParameters();

		switch (stub.getFunction()) {
			case "createVideoAsset":
				return this.createVideoAsset(stub, params);
			case "queryByHash":
				return this.queryByHash(stub, params);
			case "queryAllCars":
				return this.queryByTimestampRange(stub, params);
			case "queryAll":
				return this.queryAll(stub);
			default:
				return newErrorResponse("No such function " + stub.getFunction() + " exist");
		}
	}

	private Response queryAll(final ChaincodeStub stub) {
		return null;
	}

	private Response queryByTimestampRange(final ChaincodeStub stub, final List<String> params) {
		return null;
	}

	private Response queryByHash(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final String videoAssetState = stub.getStringState(key);

		if (videoAssetState.isEmpty()) {
			return newErrorResponse("Video asset with hash:" + key + " doesn't exit");
		}

		return newSuccessResponse(videoAssetState);
	}

	@Transaction
	private Response createVideoAsset(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final VideoAsset videoAsset = this.mapParamsToVideoAsset(params);
		final String videoAssetState = GENSON.serialize(videoAsset);

		stub.putStringState(key, videoAssetState);

		return newSuccessResponse(videoAssetState);
	}

	private VideoAsset mapParamsToVideoAsset(final List<String> params) {
		final String hash = params.get(0);
		final String timestamp = params.get(1);

		return VideoAsset
				.builder()
				.hash(hash)
				.timestamp(timestamp)
				.build();
	}

	public static void main(String[] args) {
		new VideoAssetChaincode().start(args);
	}
}
