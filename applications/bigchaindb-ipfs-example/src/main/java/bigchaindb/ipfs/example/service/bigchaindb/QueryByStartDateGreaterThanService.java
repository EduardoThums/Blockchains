package bigchaindb.ipfs.example.service.bigchaindb;

import bigchaindb.ipfs.example.model.VideoAssetModel;
import bigchaindb.ipfs.example.projection.AssetProjection;
import bigchaindb.ipfs.example.repository.VideoAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByStartDateGreaterThanService {

	private VideoAssetRepository videoAssetRepository;

	public QueryByStartDateGreaterThanService(VideoAssetRepository videoAssetRepository) {
		this.videoAssetRepository = videoAssetRepository;
	}

	public List<VideoAssetModel> queryByStartDateGreaterThanService(Long startDate) {
		return videoAssetRepository.findByStartDateGreaterThan(startDate)
				.stream()
				.map(AssetProjection::getData)
				.collect(Collectors.toList());
	}
}
