package ipfs.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidIpfsHashException extends RuntimeException {
	public InvalidIpfsHashException() {
		super("Invalid IPFS hash");
	}
}
