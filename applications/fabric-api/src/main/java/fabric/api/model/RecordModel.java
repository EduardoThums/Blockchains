package fabric.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author eduardo.thums
 */
@Getter
@NoArgsConstructor
public class RecordModel implements Serializable {

	private static final long serialVersionUID = 2951898870905124036L;

	private Long cameraId;

	private Long startDate;

	private Long endDate;

	private Long logStartDate;

	private String contentHash;

	private String storageHash;

	public String[] toArguments() {
		return Stream.of(
				this.startDate.toString(),
				this.endDate.toString(),
				this.storageHash,
				this.contentHash,
				this.cameraId.toString())
				.toArray(String[]::new);
	}
}
