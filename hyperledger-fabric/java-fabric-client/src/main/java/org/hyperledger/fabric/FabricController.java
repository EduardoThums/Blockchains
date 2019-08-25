package org.hyperledger.fabric;

import org.hyperledger.fabric.model.VideoAssetModel;
import org.hyperledger.fabric.service.fabric.FabricService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fabric")
public class FabricController {

	private FabricService fabricService;

	public FabricController(FabricService fabricService) {
		this.fabricService = fabricService;
	}

	@PostMapping("/create")
	public VideoAssetModel createVideoAsset(@RequestParam String hash) {
		return fabricService.createVideoAsset(hash);
	}

	@GetMapping("/query-by-hash")
	public VideoAssetModel queryByHash(@RequestParam String hash) {
		return fabricService.queryByHash(hash);
	}
}
