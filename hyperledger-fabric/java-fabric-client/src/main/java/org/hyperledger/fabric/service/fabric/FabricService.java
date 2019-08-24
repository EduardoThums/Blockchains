package org.hyperledger.fabric.service.fabric;

import org.hyperledger.fabric.model.VideoAssetModel;

public interface FabricService {

	VideoAssetModel createVideoAsset(String hash);

	VideoAssetModel queryByHash(String hash);
}
