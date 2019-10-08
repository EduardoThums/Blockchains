package fabric.ipfs.example.component.chaincode.videoasset.function;

import fabric.ipfs.example.component.chaincode.BaseChaincodeFunction;

/**
 * @author eduardo.thums
 */
public class QueryByCameraIdAndTimestampRangeFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByCameraIdAndTimestampRange";

	public QueryByCameraIdAndTimestampRangeFunction(String[] arguments) {
		super(NAME, arguments);
	}
}
