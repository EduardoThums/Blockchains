package bigchaindb.driver.example.service.bigchaindb;

import bigchaindb.driver.example.controller.bigchaindb.request.CreateTransactionRequest;
import com.bigchaindb.model.Transaction;

import java.io.IOException;
import java.util.List;

public interface BigchaindbService {

	List<Transaction> createTransaction(String publicKeyParam, String privateKeyParam, CreateTransactionRequest request) throws Exception;

	Transaction transferTransaction(String transactionID, String ownerPublicKeyParam, String ownerPrivateKeyParam, String newOwnerPublicKeyParam) throws Exception;

	List<Transaction> findTransactionByID(List<String> transactionIds) throws IOException;
}
