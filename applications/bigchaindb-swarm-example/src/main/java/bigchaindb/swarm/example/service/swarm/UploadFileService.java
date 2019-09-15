package bigchaindb.swarm.example.service.swarm;

import bigchaindb.swarm.example.client.SwarmClient;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class UploadFileService {

	private SwarmClient swarmClient;

	public UploadFileService(final SwarmClient swarmClient) {
		this.swarmClient = swarmClient;
	}

	public String uploadFile(final byte[] file) {
		return swarmClient.uploadFile(file);
	}
}
