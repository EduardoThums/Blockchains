package chaincode;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Builder
class VideoAsset implements Serializable {

	private Instant starDate;

	private Instant endDate;

	private String storageHash;

	private String contentHash;

	private long cameraId;
}
