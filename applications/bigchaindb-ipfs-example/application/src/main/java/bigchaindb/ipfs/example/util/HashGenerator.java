package bigchaindb.ipfs.example.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class HashGenerator {

	public String generate(byte[] record) {
		return DigestUtils.sha256Hex(record);
	}
}
