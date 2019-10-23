package ipfs.api.model;

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
public class RecordModel implements Serializable {

	private static final long serialVersionUID = 6541580349079241682L;

	private Long cameraId;

	private Long startDate;

	private Long endDate;

	private byte[] file;

	private String contentHash;

	private String storageHash;

	public RecordModel(Long cameraId, Long startDate, Long endDate, String contentHash, String storageHash) {
		this.cameraId = cameraId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contentHash = contentHash;
		this.storageHash = storageHash;
	}
}
