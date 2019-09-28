package chaincode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class VideoAsset implements Serializable {

	private static final long serialVersionUID = -2196623511234325761L;

	private Instant starDate;

	private Instant endDate;

	private String storageHash;

	private String contentHash;

	private long cameraId;
}
