package fabric.api.component.chaincode.videoasset.function;

import fabric.api.component.chaincode.BaseChaincodeFunction;

/**
 * @author eduardo.thums
 */
public class QueryByCameraIdFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryByCameraId";

	public QueryByCameraIdFunction(String[] arguments) {
		super(NAME, arguments);
	}
}
