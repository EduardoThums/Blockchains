package fabric.api.model;

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

	private static final long serialVersionUID = 2951898870905124036L;

	private Long cameraId;

	private Long startDate;

	private Long endDate;

	private byte[] file;

	private String contentHash;

	private String storageHash;
}
