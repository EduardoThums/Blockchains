package org.hyperledger.fabric.mapper;

import com.google.gson.Gson;
import lombok.Builder;
import org.hyperledger.fabric.model.VideoAssetModel;

import java.util.Collections;
import java.util.List;

@Builder
public class VideoAssetMapper {

	private Gson gson;

	public VideoAssetMapper(Gson gson) {
		this.gson = gson;
	}

	public List<String> toHashList(final String videoAssetList) {
		final List<String> hashList = Collections.singletonList(videoAssetList);

		hashList.stream().map(videoAssetStringState -> {
			final VideoAssetModel videoAsset = gson.fromJson(videoAssetStringState, VideoAssetModel.class);

			return videoAsset.getHash();
		});

		return Collections.emptyList();
	}
}
