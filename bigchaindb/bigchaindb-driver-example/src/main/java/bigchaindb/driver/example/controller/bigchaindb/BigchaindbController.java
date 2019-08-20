package bigchaindb.driver.example.controller.bigchaindb;

import bigchaindb.driver.example.controller.bigchaindb.request.CreateTransactionRequest;
import bigchaindb.driver.example.service.bigchaindb.BigchaindbService;
import com.bigchaindb.model.Asset;
import com.bigchaindb.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bigchaindb")
public class BigchaindbController {

	private BigchaindbService bigchaindbService;

	public BigchaindbController(BigchaindbService bigchaindbService) {
		this.bigchaindbService = bigchaindbService;
	}

	@PostMapping("/create")
	public ResponseEntity<List<String>> create(
			@RequestParam("publicKey") String publicKey,
			@RequestParam("privateKey") String privateKey,
			@RequestBody CreateTransactionRequest request) throws Exception {
		final List<Transaction> transactions = bigchaindbService.createTransaction(publicKey, privateKey, request);

		return ResponseEntity.ok(transactions.stream().map(Transaction::getId).collect(Collectors.toList()));
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

	@GetMapping("/find-all-by-id")
	public ResponseEntity<List<Asset>> findByID(@RequestParam("transactionsIds") List<String> transactionsIds) throws IOException {
		final List<Transaction> transactions = bigchaindbService.findTransactionByID(transactionsIds);

		return ResponseEntity.ok(transactions.stream().map(Transaction::getAsset).collect(Collectors.toList()));
	}
}
