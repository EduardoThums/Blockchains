package bigchaindb.ipfs.example.service.bigchaindb;

import java.util.List;

public interface BigchaindbService {

	List<String> createTransaction(List<String> hashes) throws Exception;
}
