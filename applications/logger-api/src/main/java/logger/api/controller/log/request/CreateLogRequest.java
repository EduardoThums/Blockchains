package logger.api.controller.log.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author eduardo.thums
 */
@Getter
@NoArgsConstructor
public class CreateLogRequest {

	@NotNull
	@Positive
	private Long startDate;

	@NotNull
	@Positive
	private Long endDate;
}
