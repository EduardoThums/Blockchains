package org.hyperledger.fabric.chaincode.fabcar.function;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

public class QueryAllCarsFunction extends BaseChaincodeFunction {

	private static final String NAME = "queryAllCars";

	private static final String[] ARGUMENTS = {""};

	public QueryAllCarsFunction() {
		super(NAME, ARGUMENTS);
	}
}
