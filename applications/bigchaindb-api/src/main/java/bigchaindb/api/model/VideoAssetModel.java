package bigchaindb.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public class VideoAssetModel implements Serializable {

	private static final long serialVersionUID = -984572621813149325L;

	private long cameraId;

	private long startDate;

	private long endDate;

	private String storageHash;

	private String contentHash;
}
