package fabric.api.service.file;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eduardo.thums
 */
@Service
public class GetFilesByHashListIpfsServiceImpl implements GetFilesByHashListService {

	private IPFS ipfsClient;

	public GetFilesByHashListIpfsServiceImpl(IPFS ipfsClient) {
		this.ipfsClient = ipfsClient;
	}

	@Override
	public List<byte[]> getFilesByHashList(List<String> hashList) throws IOException {
		final List<byte[]> videoList = new ArrayList<>();

		for (String hash : hashList) {
			ipfsClient.cat(Multihash.fromBase58(hash));
		}

		return videoList;
	}
}
