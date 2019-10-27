package swarm.api.service.swarm;

import org.springframework.stereotype.Service;
import swarm.api.client.SwarmClient;

/**
 * @author eduardo.thums
 */
@Service
public class UploadFileService {

	private SwarmClient swarmClient;

	public UploadFileService(SwarmClient swarmClient) {
		this.swarmClient = swarmClient;
	}

	public String uploadFile(byte[] file) {
		return swarmClient.uploadFile(file);
	}
}
