package fabric.ipfs.example.controller.fabric;

import fabric.ipfs.example.service.fabric.FindTransactionByHash;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fabric")
public class FabricController {

	private FindTransactionByHash findTransactionByHash;

	public FabricController(FindTransactionByHash findTransactionByHash) {
		this.findTransactionByHash = findTransactionByHash;
	}

	@GetMapping
	public ResponseEntity<String> findByHash(@RequestParam("hash") String hash) throws InvalidArgumentException, ProposalException {
		return ResponseEntity.ok(findTransactionByHash.findByHash(hash));
	}
}
