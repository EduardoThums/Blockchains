package fabric.swarm.example.component.chaincode.videoasset.function;

import fabric.swarm.example.component.chaincode.BaseChaincodeFunction;
import org.springframework.stereotype.Component;

@Component
public class CreateVideoAssetFunction extends BaseChaincodeFunction {

	private static final String NAME = "createVideoAsset";

	public CreateVideoAssetFunction(final String[] arguments) {
		super(NAME, arguments);
	}
}
