package fabric.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author eduardo.thums
 */
@Getter
@NoArgsConstructor
public class VideoAssetModel {

	private long cameraId;

	private long startDate;

	private long endDate;

	private String contentHash;

	private String storageHash;
}
