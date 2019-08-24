package fabric.ipfs.example.controller.fabric;

import fabric.ipfs.example.service.fabric.FabricService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fabric")
public class FabricController {

	private FabricService fabricService;

	public FabricController(FabricService fabricService) {
		this.fabricService = fabricService;
	}

	@GetMapping("/find-by-hash/{hash}")
	public ResponseEntity<String> findByHash(@PathVariable("hash") String hash) throws InvalidArgumentException, ProposalException {
		return ResponseEntity.ok(fabricService.findByHash(hash));
	}
}
