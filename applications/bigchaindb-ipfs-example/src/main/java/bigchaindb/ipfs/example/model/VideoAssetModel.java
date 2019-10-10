package bigchaindb.ipfs.example.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author eduardo.thums
 */
@Getter
@Builder
public class VideoAssetModel {

	private Instant startDate;

	private Instant endDate;

	private String storageHash;

	private String contentHash;

	private long cameraId;

	public Map<String, String> mapToMapObject() {
		return new TreeMap<>() {{
			put("startDate", String.valueOf(startDate.getEpochSecond()));
			put("endDate", String.valueOf(endDate.getEpochSecond()));
			put("storageHash", storageHash);
			put("contentHash", contentHash);
			put("cameraId", String.valueOf(cameraId));
		}};
	}
}
