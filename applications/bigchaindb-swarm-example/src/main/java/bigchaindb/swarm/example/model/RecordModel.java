package bigchaindb.swarm.example.model;

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

	private byte[] value;

	public RecordModel(Long cameraId, byte[] value) {
		this.cameraId = cameraId;
		this.value = value;
	}
}
