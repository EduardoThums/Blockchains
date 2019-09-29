package chaincode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class VideoAsset implements Serializable {

	private static final long serialVersionUID = 1162978438007020979L;

	private long starDate;

	private long endDate;

	private String storageHash;

	private String contentHash;

	private long cameraId;
}
