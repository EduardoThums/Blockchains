package bigchaindb.ipfs.example.model;

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

	private Long cameraId;

	private byte[] videoRecord;

	public RecordModel(Long cameraId, byte[] videoRecord) {
		this.cameraId = cameraId;
		this.videoRecord = videoRecord;
	}
}
