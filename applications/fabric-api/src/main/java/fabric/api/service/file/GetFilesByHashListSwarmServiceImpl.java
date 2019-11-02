package fabric.api.service.file;

import fabric.api.component.swarm.SwarmClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class GetFilesByHashListSwarmServiceImpl implements GetFilesByHashListService {

	private SwarmClient swarmClient;

	public GetFilesByHashListSwarmServiceImpl(SwarmClient swarmClient) {
		this.swarmClient = swarmClient;
	}

	@Override
	public List<byte[]> getFilesByHashList(List<String> hashList) {
		return hashList
				.stream()
				.map(hash -> swarmClient.getFileByHash(hash))
				.collect(Collectors.toList());
	}
}
