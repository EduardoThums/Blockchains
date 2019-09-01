package bigchaindb.ipfs.example.service.bigchaindb;

import com.bigchaindb.model.Transaction;

import java.io.IOException;
import java.util.List;

public interface BigchaindbService {

    List<String> createTransaction(String ipfsHash, String contentHash) throws Exception;

    Transaction findTransactionById(String transactionId) throws IOException;
}
