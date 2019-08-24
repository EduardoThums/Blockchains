package fabric.ipfs.example.component.chaincode.videoasset.function;

import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;

public class QueryByHashFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByHash";

	public QueryByHashFunction(String[] arguments) {
		super(NAME, arguments);
	}
}
