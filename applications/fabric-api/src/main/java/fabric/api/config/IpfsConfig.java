package fabric.api.config;

import org.springframework.beans.factory.annotation.Value;
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

//	@Bean
//	public IPFS ipfs() {
//		return new IPFS(new MultiAddress(ipfsUrl));
//	}
}
