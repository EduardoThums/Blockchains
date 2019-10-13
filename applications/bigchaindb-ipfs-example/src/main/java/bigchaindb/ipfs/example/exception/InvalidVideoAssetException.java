package bigchaindb.ipfs.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidVideoAssetException extends RuntimeException {

	public InvalidVideoAssetException() {
		super("Video asset doesn't exist");
	}
}
