package org.hyperledger.fabric.chaincode.videoasset.function;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class CreateVideoAssetFunction extends BaseChaincodeFunction {

	private static final String NAME = "createVideoAsset";

	private static final String[] ARGUMENTS = {"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", "2015-11-13 13:50:21.007"};

	public CreateVideoAssetFunction() {
		super(NAME, ARGUMENTS);
	}
}
