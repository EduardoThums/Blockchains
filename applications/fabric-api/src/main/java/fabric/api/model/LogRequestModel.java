package fabric.api.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class LogRequestModel implements Serializable {

	private static final long serialVersionUID = -2034777658486321845L;

	private Long startDate;

	private Long endDate;
}
