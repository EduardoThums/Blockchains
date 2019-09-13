package fabric.swarm.example.component.chaincode;

import lombok.Getter;

@Getter
public abstract class BaseChaincode {

	private String name;

	private String version;

	private BaseChaincodeFunction function;

	public BaseChaincode(final String name, final String version, final BaseChaincodeFunction function) {
		this.name = name;
		this.version = version;
		this.function = function;
	}
}
