package bigchaindb.ipfs.example.service.bigchaindb;

import bigchaindb.ipfs.example.exception.InvalidVideoAssetException;
import bigchaindb.ipfs.example.model.VideoAssetModel;
import bigchaindb.ipfs.example.projection.AssetProjection;
import bigchaindb.ipfs.example.repository.VideoAssetRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByAssetIdService {

	private VideoAssetRepository videoAssetRepository;

	public QueryByAssetIdService(VideoAssetRepository videoAssetRepository) {
		this.videoAssetRepository = videoAssetRepository;
	}

	public VideoAssetModel queryByAssetById(String id) {
		return videoAssetRepository.findById(id)
				.map(AssetProjection::getData)
				.orElseThrow(InvalidVideoAssetException::new);
	}
}
