package org.hyperledger.fabric.chaincode.fabcar;

import org.hyperledger.fabric.chaincode.BaseChaincode;
import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class FabCarChaincode extends BaseChaincode {

	private static final String NAME = "videoassetcc";

	private static final String VERSION = "1.0";

	public FabCarChaincode(final BaseChaincodeFunction function) {
		super(NAME, VERSION, function);
	}
}
