package fabric.api.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class LogRequestModel implements Serializable {

	private static final long serialVersionUID = -2034777658486321845L;

	private Instant startDate;

	private Instant endDate;
}
