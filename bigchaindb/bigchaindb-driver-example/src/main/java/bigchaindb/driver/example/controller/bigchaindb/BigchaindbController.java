package bigchaindb.driver.example.controller.bigchaindb;

import bigchaindb.driver.example.controller.bigchaindb.request.CreateTransactionRequest;
import bigchaindb.driver.example.service.bigchaindb.BigchaindbService;
import com.bigchaindb.model.Asset;
import com.bigchaindb.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/bigchaindb")
public class BigchaindbController {

	private BigchaindbService bigchaindbService;

	public BigchaindbController(BigchaindbService bigchaindbService) {
		this.bigchaindbService = bigchaindbService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> create(
			@RequestParam("publicKey") String publicKey,
			@RequestParam("privateKey") String privateKey,
			@RequestBody CreateTransactionRequest request) throws Exception {
		final Transaction createTransaction = bigchaindbService.createTransaction(publicKey, privateKey, request);

		return ResponseEntity.ok(createTransaction.getId());
	}

	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(
			@RequestParam("transactionID") String transactionID,
			@RequestParam("ownerPublicKeyParam") String ownerPublicKeyParam,
			@RequestParam("ownerPrivateKeyParam") String ownerPrivateKeyParam,
			@RequestParam("newOwnerPublicKeyParam") String newOwnerPublicKeyParam) throws Exception {
		final Transaction transferTransaction = bigchaindbService.transferTransaction(transactionID, ownerPublicKeyParam, ownerPrivateKeyParam, newOwnerPublicKeyParam);

		return ResponseEntity.ok(transferTransaction.getId());
	}

	@GetMapping("/find-by-id/{transactionID}")
	public ResponseEntity<Asset> findByID(@PathVariable("transactionID") String transactionID) throws IOException {
		final Transaction transaction = bigchaindbService.findTransactionByID(transactionID);

		return ResponseEntity.ok(transaction.getAsset());
	}
}
