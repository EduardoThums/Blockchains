package fabric.ipfs.example.component.chaincode.videoasset.function;

import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;

public class CreateVideoAssetFunction extends BaseChaincodeFunction {

	private static final String NAME = "createVideoAsset";

	public CreateVideoAssetFunction(final String[] arguments) {
		super(NAME, arguments);
	}
}
