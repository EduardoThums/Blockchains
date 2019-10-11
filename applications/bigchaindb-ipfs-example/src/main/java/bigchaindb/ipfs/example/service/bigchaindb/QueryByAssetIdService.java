package bigchaindb.ipfs.example.service.bigchaindb;

import bigchaindb.ipfs.example.model.AssetModel;
import bigchaindb.ipfs.example.model.VideoAssetModel;
import bigchaindb.ipfs.example.repository.VideoAssetRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByAssetIdService {

	private Gson gson;

	private VideoAssetRepository videoAssetRepository;

	public QueryByAssetIdService(VideoAssetRepository videoAssetRepository) {
		this.gson = new Gson();
		this.videoAssetRepository = videoAssetRepository;
	}

	public VideoAssetModel queryByAssetById(String id) {
		final AssetModel assetModel = videoAssetRepository.findById(id);

		return assetModel.getData();
//		return gson.fromJson(json, VideoAssetModel.class);
	}
}
