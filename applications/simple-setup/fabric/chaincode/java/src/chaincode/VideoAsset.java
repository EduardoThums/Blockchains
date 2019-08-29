package chaincode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class VideoAsset {

	private String ipfsHash;

	private String contentHash;
}
