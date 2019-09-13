package fabric.swarm.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

	public TransactionNotFoundException(String transactionHash) {
		super(String.format("Transaction %s not found", transactionHash));
	}
}
