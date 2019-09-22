package fabric.ipfs.example.service.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author eduardo.thums
 */
@Service
public class GetFileByHashService {

	private static final String NEW_VIDEO_PATH = "/home/eduardo/Videos/new-video.mp4";

	private IPFS ipfsClient;

	public GetFileByHashService(IPFS ipfsClient) {
		this.ipfsClient = ipfsClient;
	}

	public String getFileByHash(String hash) throws IOException {
		final byte[] bytes = ipfsClient.cat(Multihash.fromBase58(hash));

		final File file = new File(Objects.requireNonNull(NEW_VIDEO_PATH));
		FileUtils.writeByteArrayToFile(file, bytes);

		return file.getPath();
	}
}
