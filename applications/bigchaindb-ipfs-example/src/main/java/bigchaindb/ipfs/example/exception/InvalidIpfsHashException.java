package bigchaindb.ipfs.example.exception;

/**
 * @author eduardo.thums
 */
public class InvalidIpfsHashException extends RuntimeException {
    public InvalidIpfsHashException() {
        super("Invalid IPFS hash");
    }
}
