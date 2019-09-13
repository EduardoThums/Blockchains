package fabric.swarm.example.component.chaincode.videoasset;

import fabric.swarm.example.component.chaincode.BaseChaincode;
import fabric.swarm.example.component.chaincode.BaseChaincodeFunction;
import org.springframework.stereotype.Component;

@Component
public class VideoAssetChaincode extends BaseChaincode {

	private static final String NAME = "videoassetcc";

	private static final String VERSION = "1.0";

	public VideoAssetChaincode(final BaseChaincodeFunction function) {
		super(NAME, VERSION, function);
	}
}
