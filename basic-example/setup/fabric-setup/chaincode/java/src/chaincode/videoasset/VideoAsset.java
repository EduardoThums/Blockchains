package chaincode.videoasset;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class VideoAsset {

	private String hash;

	private String timestamp;
}
