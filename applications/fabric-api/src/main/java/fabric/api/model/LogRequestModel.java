package fabric.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author eduardo.thums
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogRequestModel {

	private Long startDate;

	private Long endDate;
}
