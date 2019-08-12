package org.hyperledger.fabric.chaincode.videoasset.function;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class QueryByTimestampRangeFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByTimestampRange";

	public QueryByTimestampRangeFunction(final String[] arguments) {
		super(NAME, arguments);
	}
}
