package bigchaindb.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author eduardo.thums
 */
@Getter
@NoArgsConstructor
public class RecordModel implements Serializable {

	private static final long serialVersionUID = -3805170559012124637L;

	private long cameraId;

	private long startDate;

	private long endDate;

	private long logStartDate;

	private String contentHash;

	private String storageHash;
}
