package bigchaindb.ipfs.example.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author eduardo.thums
 */
@Getter
//@AllArgsConstructor
public class VideoAssetModel implements Serializable {

	private static final long serialVersionUID = -9061180728548775137L;

	public VideoAssetModel(Long startDate, Long endDate, String storageHash, String contentHash, Long cameraId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.storageHash = storageHash;
		this.contentHash = contentHash;
		this.cameraId = cameraId;
	}

	@Field("startDate")
	private Long startDate;

	@Field("endDate")
	private Long endDate;

	@Field("storageHash")
	private String storageHash;

	@Field("contentHash")
	private String contentHash;

	@Field("cameraId")
	private Long cameraId;
}
