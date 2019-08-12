package org.hyperledger.fabric.chaincode.videoasset.function;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class QueryByHashFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByHash";

	public QueryByHashFunction(final String[] arguments) {
		super(NAME, arguments);
	}
}
