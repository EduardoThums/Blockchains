package chaincode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
class VideoAssetWrapper {

	private List<String> videoAssetList;
}
