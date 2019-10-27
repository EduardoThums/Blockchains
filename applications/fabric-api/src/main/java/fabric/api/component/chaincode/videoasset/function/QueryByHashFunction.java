package fabric.api.component.chaincode.videoasset.function;

import fabric.api.component.chaincode.BaseChaincodeFunction;

public class QueryByHashFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByHash";

	public QueryByHashFunction(String[] arguments) {
		super(NAME, arguments);
	}
}
