package fabric.ipfs.example.service.ipfs;

import fabric.ipfs.example.config.IPFSConfig;
import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IPFSServiceImpl implements IPFSService {

	private static final String NEW_VIDEO_PATH = "/home/eduardo/Downloads/new-video.mp4";

	private static final String NEW_IPFS_VIDEO_PATH = "/home/eduardo/Downloads/new-ipfs-video.mp4";

	private IPFS instance;

	public IPFSServiceImpl() {
		this.instance = new IPFS(new MultiAddress(IPFSConfig.IPFS_DAEMON_ADDRESS.getValue()));
	}

	@Override
	public List<String> insert(byte[] record) throws IOException {
		final NamedStreamable.ByteArrayWrapper byteArrayWrapper = new NamedStreamable.ByteArrayWrapper(record);

		final FileOutputStream fileOutputStream = new FileOutputStream(new File(NEW_VIDEO_PATH));
		fileOutputStream.write(record);
		fileOutputStream.close();

		final File file = new File(Objects.requireNonNull(NEW_VIDEO_PATH));

		return this.instance.add(byteArrayWrapper)
				.stream()
				.map(merkleNode -> merkleNode.hash.toBase58())
				.collect(Collectors.toList());
	}

	@Override
	public void findFileByHash(String hash) throws IOException {
		final Multihash multihash = Multihash.fromBase58(hash);
		final byte[] fileByte = this.instance.cat(multihash);

		final FileOutputStream fileOutputStream = new FileOutputStream(new File(NEW_IPFS_VIDEO_PATH));
		fileOutputStream.write(fileByte);
		fileOutputStream.close();

		final File file = new File(Objects.requireNonNull(NEW_IPFS_VIDEO_PATH));
		log.info("Received record and converted to video file with the following path {}", file.getPath());
	}
}
