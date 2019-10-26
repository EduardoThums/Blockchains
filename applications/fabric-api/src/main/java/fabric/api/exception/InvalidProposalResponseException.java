package fabric.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidProposalResponseException extends RuntimeException {

	public InvalidProposalResponseException() {
		super("Proposal response doesn't exists");
	}
}
