package org.hyperledger.fabric.chaincode.videoasset;

import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class VideoAssetChaincode extends BaseChaincode {

	private static final String NAME = "videoassetcc";

	private static final String VERSION = "1.0";

	public VideoAssetChaincode(final BaseChaincodeFunction function) {
		super(NAME, VERSION, function);
	}
}
