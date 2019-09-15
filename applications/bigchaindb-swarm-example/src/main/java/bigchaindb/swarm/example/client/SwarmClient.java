package bigchaindb.swarm.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author eduardo.thums
 */
@FeignClient(value = "${swarm.value}", url = "${swarm.url}:")
public interface SwarmClient {

	@RequestMapping(method = RequestMethod.POST, value = "/")
	String uploadFile(@RequestBody byte[] file);

	@RequestMapping(method = RequestMethod.GET, value = "/{hash}/video.mp4")
	byte[] getFileByHash(@PathVariable("hash") String hash);
}
