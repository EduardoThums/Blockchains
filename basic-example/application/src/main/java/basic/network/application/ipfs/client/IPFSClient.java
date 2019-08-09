package basic.network.application.ipfs.client;

import basic.network.application.config.IPFSConfig;
import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import lombok.Getter;

import java.io.IOException;

@Getter
public class IPFSClient {

	private IPFS instance;

	public IPFSClient() {
		this.instance = new IPFS(new MultiAddress(IPFSConfig.IPFS_DAEMON_ADDRESS.getValue()));
	}

	public Multihash insert(final byte[] record) throws IOException {
		final ByteArrayWrapper byteArrayWrapper = new ByteArrayWrapper(record);

		return this.instance.add(byteArrayWrapper).get(0).hash;
	}
}
