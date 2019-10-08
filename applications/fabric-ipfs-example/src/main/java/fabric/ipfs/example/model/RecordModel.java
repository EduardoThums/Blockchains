package fabric.ipfs.example.model;

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

	private static final long serialVersionUID = 6893060907025723304L;

	private long cameraId;

	private byte[] videoRecord;

	public RecordModel(Long cameraId, byte[] videoRecord) {
		this.cameraId = cameraId;
		this.videoRecord = videoRecord;
	}
}
