package bigchaindb.ipfs.example.controller.bigchaindb;

import bigchaindb.ipfs.example.service.bigchaindb.BigchainDbService;
import com.bigchaindb.model.Asset;
import com.bigchaindb.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller for access the BigchainDB information.
 *
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/bigchaindb")
public class BigchaindbController {

    private BigchainDbService bigchaindbService;

    public BigchaindbController(BigchainDbService bigchaindbService) {
        this.bigchaindbService = bigchaindbService;
    }

    /**
     * Find Transaction object asset property by their id.
     *
     * @param transactionId the transaction id in hash form.
     * @return Asset the asset property of the Transaction object
     */
    @GetMapping("/find-transaction-by-id/{transactionId}")
    public ResponseEntity<Asset> findTransactionById(@PathVariable("transactionId") String transactionId) throws IOException {
        final Transaction transaction = bigchaindbService.findTransactionById(transactionId);

        return ResponseEntity.ok(transaction.getAsset());
    }
}
