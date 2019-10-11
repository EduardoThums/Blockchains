package bigchaindb.ipfs.example.service.bigchaindb;

import bigchaindb.ipfs.example.model.VideoAssetModel;
import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

/**
 * @author eduardo.thums
 */
@Service
public class FindTransactionByIdService {

	private Gson gson;

	private ObjectMapper objectMapper;

	public FindTransactionByIdService() {
		this.gson = new Gson();
		this.objectMapper = new ObjectMapper();
	}

	public Transaction findTransactionById(String transactionId) throws IOException {
		final Transaction transaction = TransactionsApi.getTransactionById(transactionId);
		final VideoAssetModel videoAssetModel = objectMapper.convertValue(transaction.getAsset().getData(), VideoAssetModel.class);

		final Instant startDate = Instant.ofEpochSecond(videoAssetModel.getStartDate());
		final Instant endDate = Instant.ofEpochSecond(videoAssetModel.getEndDate());

		return TransactionsApi.getTransactionById(transactionId);
	}
}
