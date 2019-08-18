package org.hyperledger.fabric.service;

import org.hyperledger.fabric.model.VideoAssetModel;

import java.util.List;

public interface FabricService {

	VideoAssetModel createVideoAsset(String hash, Integer year);

	VideoAssetModel queryByHash(String hash);

	List<VideoAssetModel> queryByYearRange(Integer year);
}
