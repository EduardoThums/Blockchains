package fabric.ipfs.example.controller.fabric;

import fabric.ipfs.example.service.fabric.FindTransactionByHash;
import fabric.ipfs.example.service.fabric.QueryByCameraIdService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fabric")
public class FabricController {

	private FindTransactionByHash findTransactionByHash;

	private QueryByCameraIdService queryByCameraIdService;

	public FabricController(FindTransactionByHash findTransactionByHash, QueryByCameraIdService queryByCameraIdService) {
		this.findTransactionByHash = findTransactionByHash;
		this.queryByCameraIdService = queryByCameraIdService;
	}

	@GetMapping
	public ResponseEntity<String> findByHash(@RequestParam("hash") String hash) throws InvalidArgumentException, ProposalException {
		return ResponseEntity.ok(findTransactionByHash.findByHash(hash));
	}

	@GetMapping("/camera/{cameraId}")
	public ResponseEntity<String> findByHash(@PathVariable("cameraId") Long cameraId) throws InvalidArgumentException, ProposalException {
		return ResponseEntity.ok(queryByCameraIdService.queryByCameraId(cameraId));
	}
}
