package bigchaindb.ipfs.example.controller.bigchaindb;

import bigchaindb.ipfs.example.service.bigchaindb.FindTransactionByIdService;
import com.bigchaindb.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/bigchaindb")
public class BigchaindbController {

	private FindTransactionByIdService findTransactionByIdService;

	public BigchaindbController(FindTransactionByIdService findTransactionByIdService) {
		this.findTransactionByIdService = findTransactionByIdService;
	}

	@GetMapping
	public ResponseEntity<Transaction> findTransactionById(@RequestParam("transactionId") String transactionId) throws IOException {
		return ResponseEntity.ok(findTransactionByIdService.findTransactionById(transactionId));
	}
}
