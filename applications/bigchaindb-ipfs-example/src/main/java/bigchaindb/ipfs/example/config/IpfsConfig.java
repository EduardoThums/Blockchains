package bigchaindb.ipfs.example.config;

import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author eduardo.thums
 */
@Configuration
public class IpfsConfig {

	private String ipfsUrl;

	public IpfsConfig(@Value("${ipfs.url}") String ipfsUrl) {
		this.ipfsUrl = ipfsUrl;
	}

	@Bean
	public IPFS ipfs() {
		return new IPFS(new MultiAddress(ipfsUrl));
	}
}
