package org.hyperledger.fabric.chaincode.fabcar.function;

import lombok.Getter;
import lombok.Setter;
import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;

@Getter
@Setter
public class CreateCarFunction extends BaseChaincodeFunction {

	private static final String NAME = "createCar";

	private static final String[] ARGUMENTS = {"CAR1", "Chevy", "Volt", "Red", "Nick"};

	public CreateCarFunction() {
		super(NAME, ARGUMENTS);
	}
}
