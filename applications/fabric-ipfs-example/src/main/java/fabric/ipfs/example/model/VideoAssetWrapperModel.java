package fabric.ipfs.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public class VideoAssetWrapperModel {

	@JsonProperty("videoAssetList")
	private List<String> videoAssetModels;
}
