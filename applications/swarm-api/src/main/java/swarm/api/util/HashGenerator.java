package swarm.api.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author eduardo.thums
 */
@Component
public class HashGenerator {

	public String generateHash(byte[] file) {
		return DigestUtils.sha256Hex(file);
	}
}
