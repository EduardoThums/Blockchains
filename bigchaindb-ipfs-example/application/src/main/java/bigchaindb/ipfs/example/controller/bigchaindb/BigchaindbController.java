package bigchaindb.ipfs.example.controller.bigchaindb;

import bigchaindb.ipfs.example.service.bigchaindb.BigchaindbService;
import com.bigchaindb.model.Asset;
import com.bigchaindb.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bigchaindb")
public class BigchaindbController {

	private BigchaindbService bigchaindbService;

	public BigchaindbController(BigchaindbService bigchaindbService) {
		this.bigchaindbService = bigchaindbService;
	}

	@GetMapping("/find-transaction-by-id/{transactionId}")
	public ResponseEntity<Asset> findTransactionById(@PathVariable("transactionId") String transactionId) throws IOException {
		final Transaction transaction = bigchaindbService.findTransactionById(transactionId);

		return ResponseEntity.ok(transaction.getAsset());
	}
}
