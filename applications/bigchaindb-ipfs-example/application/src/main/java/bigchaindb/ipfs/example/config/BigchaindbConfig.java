package bigchaindb.ipfs.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BigchaindbConfig {

    PEER0_URL("http://localhost:33193"),

    PEER1_URL("http://localhost:33199"),

    PEER2_URL("http://localhost:33205"),

    PEER3_URL("http://localhost:33211"),

    DEFAULT_OUTPUT_AMOUNT("1");

    private String value;
}
