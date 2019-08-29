package bigchaindb.ipfs.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BigchaindbConfig {

    //TODO: Change this configs to application.yml
    PEER0_URL("http://localhost:32917"),

    PEER1_URL("http://localhost:32851"),

    PEER2_URL("http://localhost:32857"),

    PEER3_URL("http://localhost:32863"),

    DEFAULT_OUTPUT_AMOUNT("1");

    private String value;
}
