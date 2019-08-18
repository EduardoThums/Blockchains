package bigchaindb.driver.example.service.bigchaindb;

import bigchaindb.driver.example.controller.bigchaindb.request.CreateTransactionRequest;
import com.bigchaindb.model.Transaction;

public interface BigchaindbService {

	Transaction createTransaction(String publicKeyParam, String privateKeyParam, CreateTransactionRequest request) throws Exception;

	Transaction transferTransaction(String transactionID, String ownerPublicKeyParam, String ownerPrivateKeyParam, String newOwnerPublicKeyParam) throws Exception;
}
