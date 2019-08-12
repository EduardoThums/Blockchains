package org.hyperledger.fabric;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;
import org.hyperledger.fabric.chaincode.videoasset.VideoAssetChaincode;
import org.hyperledger.fabric.chaincode.videoasset.function.CreateVideoAssetFunction;
import org.hyperledger.fabric.chaincode.videoasset.function.QueryByHashFunction;
import org.hyperledger.fabric.chaincode.videoasset.function.QueryByTimestampRangeFunction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabric-controller")
public class FabricController {

	private FabricService fabricService;

	public FabricController(FabricService fabricService) {
		this.fabricService = fabricService;
	}

	@PostMapping("/create-video-asset")
	public List<String> createVideoAsset(@RequestParam String hash, @RequestParam Integer year) {
		final String[] arguments = {hash, String.valueOf(year)};
		final BaseChaincodeFunction baseChaincodeFunction = new CreateVideoAssetFunction(arguments);

		return this.fabricService.callChaincode(new VideoAssetChaincode(baseChaincodeFunction));
	}

	@GetMapping("/query-by-hash")
	public List<String> queryByHash(@RequestParam final String hash) {
		final String[] arguments = {hash};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByHashFunction(arguments);

		return this.fabricService.callChaincode(new VideoAssetChaincode(baseChaincodeFunction));
	}

	@GetMapping("/query-by-timestamp-range")
	public List<String> queryByTimestampRange(@RequestParam final Integer year) {
		final String[] arguments = {String.valueOf(year)};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByTimestampRangeFunction(arguments);

		return this.fabricService.callChaincode(new VideoAssetChaincode(baseChaincodeFunction));
	}
}
