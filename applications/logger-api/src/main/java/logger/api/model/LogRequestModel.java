package logger.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author eduardo.thums
 */
@Getter
@Setter
@NoArgsConstructor
public class LogRequestModel implements Serializable {

	private static final long serialVersionUID = -6265877015973185713L;

	private Long startDate;

	private Long endDate;
}
