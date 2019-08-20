package bigchaindb.ipfs.example.service.ipfs;

import bigchaindb.ipfs.example.config.IPFSConfig;
import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multiaddr.MultiAddress;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IPFSServiceImpl implements IPFSService {

	private IPFS instance;

	public IPFSServiceImpl() {
		this.instance = new IPFS(new MultiAddress(IPFSConfig.IPFS_DAEMON_ADDRESS.getValue()));
	}

	@Override
	public List<String> insert(byte[] record) throws IOException {
		final ByteArrayWrapper byteArrayWrapper = new ByteArrayWrapper(record);

		return this.instance.add(byteArrayWrapper)
				.stream()
				.map(merkleNode -> merkleNode.hash.toString())
				.collect(Collectors.toList());
	}
}
