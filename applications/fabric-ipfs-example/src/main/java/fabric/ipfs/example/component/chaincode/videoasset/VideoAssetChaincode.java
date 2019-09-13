package fabric.ipfs.example.component.chaincode.videoasset;

import fabric.ipfs.example.component.chaincode.BaseChaincode;
import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;

public class VideoAssetChaincode extends BaseChaincode {

	private static final String NAME = "videoassetcc";

	private static final String VERSION = "1.0";

	public VideoAssetChaincode(final BaseChaincodeFunction function) {
		super(NAME, VERSION, function);
	}
}
