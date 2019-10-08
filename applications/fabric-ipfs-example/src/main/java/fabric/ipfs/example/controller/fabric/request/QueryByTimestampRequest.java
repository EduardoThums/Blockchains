package fabric.ipfs.example.controller.fabric.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author eduardo.thums
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueryByTimestampRequest {

	@NotNull
	private Instant startDate;

	@NotNull
	private Instant endDate;
}
