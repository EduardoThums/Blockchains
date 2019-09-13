package fabric.swarm.example.component.chaincode;

import lombok.Getter;

@Getter
public abstract class BaseChaincodeFunction {

	private String name;

	private String[] arguments;

	public BaseChaincodeFunction(final String name, final String[] arguments) {
		this.name = name;
		this.arguments = arguments;
	}
}
