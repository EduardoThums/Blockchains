package org.hyperledger.fabric;

import org.hyperledger.fabric.chaincode.BaseChaincodeFunction;
import org.hyperledger.fabric.chaincode.videoasset.function.QueryByTimestampRangeFunction;
import org.hyperledger.fabric.model.VideoAssetModel;
import org.hyperledger.fabric.service.FabricService;
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
	public VideoAssetModel createVideoAsset(@RequestParam String hash, @RequestParam Integer year) {
		return fabricService.createVideoAsset(hash, year);
	}

	@GetMapping("/query-by-hash")
	public VideoAssetModel queryByHash(@RequestParam final String hash) {
		return fabricService.queryByHash(hash);
	}

	@GetMapping("/query-by-timestamp-range")
	public List<VideoAssetModel> queryByTimestampRange(@RequestParam final Integer year) {
		final String[] arguments = {String.valueOf(year)};
		final BaseChaincodeFunction baseChaincodeFunction = new QueryByTimestampRangeFunction(arguments);

		return fabricService.queryByYearRange(year);
	}
}
