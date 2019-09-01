package bigchaindb.ipfs.example.util;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

@Component
public class HashGenerator {

    public String generate(byte[] record) {
        return Hashing.sha256()
                .hashBytes(record)
                .toString();
    }
}
