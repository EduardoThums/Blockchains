package basic.network.application.fabric.chaincode.videoasset;

import basic.network.application.fabric.chaincode.BaseChaincode;
import basic.network.application.fabric.chaincode.BaseChaincodeFunction;

public class VideoAssetChaincode extends BaseChaincode {

	private static final String NAME = "videoassetcc";

	private static final String VERSION = "1.0";

	public VideoAssetChaincode(final BaseChaincodeFunction function) {
		super(NAME, VERSION, function);
	}
}
