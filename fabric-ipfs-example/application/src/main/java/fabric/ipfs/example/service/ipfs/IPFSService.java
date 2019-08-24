package fabric.ipfs.example.service.ipfs;

import java.io.IOException;
import java.util.List;

public interface IPFSService {

	List<String> insert(byte[] record) throws IOException;

	void findFileByHash(String hash) throws IOException;
}
