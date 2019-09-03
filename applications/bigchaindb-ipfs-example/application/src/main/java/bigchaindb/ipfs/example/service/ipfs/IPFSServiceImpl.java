package bigchaindb.ipfs.example.service.ipfs;

import bigchaindb.ipfs.example.exception.InvalidIpfsHashException;
import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class IPFSServiceImpl implements IPFSService {

	private static final String NEW_IPFS_VIDEO_PATH = "/home/eduardo/Downloads/new-ipfs-video.mp4";

	private IPFS instance;

	public IPFSServiceImpl(@Value("${spring.ipfs.daemonAddress}") final String daemonAddress) {
		this.instance = new IPFS(new MultiAddress(daemonAddress));
	}

	@Override
	public String insert(final byte[] record) throws IOException {
		final ByteArrayWrapper byteArrayWrapper = new ByteArrayWrapper(record);

		return this.instance.add(byteArrayWrapper)
				.stream()
				.map(merkleNode -> merkleNode.hash.toBase58())
				.findFirst()
				.orElseThrow(InvalidIpfsHashException::new);
	}

	@Override
	public String findFileByHash(final String hash) throws IOException {
		final Multihash multihash = Multihash.fromBase58(hash);
		final byte[] fileByte = this.instance.cat(multihash);

		final FileOutputStream fileOutputStream = new FileOutputStream(new File(NEW_IPFS_VIDEO_PATH));
		fileOutputStream.write(fileByte);
		fileOutputStream.close();

		final File file = new File(Objects.requireNonNull(NEW_IPFS_VIDEO_PATH));

		return file.getPath();
	}
}
