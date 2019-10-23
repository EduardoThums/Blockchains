package logger.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author eduardo.thums
 */
@Getter
@Setter
@NoArgsConstructor
public class LogRequestModel implements Serializable {

	private static final long serialVersionUID = -6265877015973185713L;

	private Instant startDate;

	private Instant endDate;
}
