package logger.api.controller.log.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author eduardo.thums
 */
@Getter
@Builder
public class LogResponse {

	private Long startDate;

	private Long endDate;
}
