package basic.network.application.fabric.chaincode.videoasset.function;

import basic.network.application.fabric.chaincode.BaseChaincodeFunction;

public class CreateVideoAssetFunction extends BaseChaincodeFunction {

	private static final String NAME = "createVideoAsset";

//	private static final String[] ARGUMENTS = {"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", "2015-11-13 13:50:21.007"};

	public CreateVideoAssetFunction(final String[] arguments) {
		super(NAME, arguments);
	}
}
