package chaincode;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
class VideoAsset {

	private Instant starDate;

	private Instant endDate;

	private String storageHash;

	private String contentHash;

	private Long cameraId;
}
