package org.hyperledger.fabric.chaincode.fabcar.function;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class QueryCarFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryCar";

	private static final String[] ARGUMENTS = {"CAR1"};

	public QueryCarFunction() {
		super(NAME, ARGUMENTS);
	}
}
