package ipfs.api.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author eduardo.thums
 */
@Component
public class HashGenerator {

	public String generateHash(byte[] file) {
		return BCrypt.hashpw(new String(file), BCrypt.gensalt());
	}
}
