package bigchaindb.ipfs.example.service.bigchaindb;

import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.model.Transaction;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author eduardo.thums
 */
@Service
public class FindTransactionByIdService {

	public Transaction findTransactionById(String transactionId) throws IOException {
		return TransactionsApi.getTransactionById(transactionId);
	}
}
