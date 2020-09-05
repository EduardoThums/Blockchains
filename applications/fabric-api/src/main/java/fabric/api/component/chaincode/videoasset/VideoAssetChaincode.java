package fabric.api.component.chaincode.videoasset;

import fabric.api.component.chaincode.BaseChaincode;
import fabric.api.component.chaincode.BaseChaincodeFunction;

public class VideoAssetChaincode extends BaseChaincode {

	private static final String NAME = "videoassetcc";

	private static final String VERSION = "1.6";

	public VideoAssetChaincode(BaseChaincodeFunction function) {
		super(NAME, VERSION, function);
	}
}
