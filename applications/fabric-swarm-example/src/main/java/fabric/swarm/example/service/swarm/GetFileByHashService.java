package fabric.swarm.example.service.swarm;

import fabric.swarm.example.client.SwarmClient;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author eduardo.thums
 */
@Service
public class GetFileByHashService {

	private SwarmClient swarmClient;

	public GetFileByHashService(SwarmClient swarmClient) {
		this.swarmClient = swarmClient;
	}

	public String getFileByHash(String hash) throws IOException {
		final File file = new File("/home/eduardo/Videos/new-video.mp4");

		final byte[] multipartFile = swarmClient.getFileByHash(hash);
		FileUtils.writeByteArrayToFile(file, multipartFile);

		return file.getPath();
	}
}
