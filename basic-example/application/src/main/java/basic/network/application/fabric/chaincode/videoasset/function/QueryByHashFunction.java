package basic.network.application.fabric.chaincode.videoasset.function;

import basic.network.application.fabric.chaincode.BaseChaincodeFunction;

public class QueryByHashFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByHash";

	private static final String[] ARGUMENTS = {"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"};

	public QueryByHashFunction() {
		super(NAME, ARGUMENTS);
	}
}
