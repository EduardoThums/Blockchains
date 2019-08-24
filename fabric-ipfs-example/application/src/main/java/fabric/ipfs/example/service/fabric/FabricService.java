package fabric.ipfs.example.service.fabric;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.List;

public interface FabricService {

	List<String> createTransaction(List<String> hashes) throws Exception;

	String findByHash(String hash) throws ProposalException, InvalidArgumentException;
}
