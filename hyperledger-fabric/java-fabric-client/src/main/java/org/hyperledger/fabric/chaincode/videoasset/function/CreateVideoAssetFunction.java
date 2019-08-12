package org.hyperledger.fabric.chaincode.videoasset.function;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class CreateVideoAssetFunction extends BaseChaincodeFunction {

	private static final String NAME = "createVideoAsset";

	public CreateVideoAssetFunction(final String[] arguments) {
		super(NAME, arguments);
	}
}
