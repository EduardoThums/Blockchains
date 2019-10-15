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
public class QueryAssetByCameraIdAndStartAndEndDateRangeService {

	private VideoAssetRepository videoAssetRepository;

	public QueryAssetByCameraIdAndStartAndEndDateRangeService(VideoAssetRepository videoAssetRepository) {
		this.videoAssetRepository = videoAssetRepository;
	}

	public List<VideoAssetModel> queryAssetByCameraIdAndStartAndEndDateRange(Long cameraId, Long startDate, Long endDate) {
		return videoAssetRepository.findByCameraIdAndStartDateAndEndDateRange(cameraId, startDate, endDate)
				.stream()
				.map(AssetProjection::getData)
				.collect(Collectors.toList());
	}
}
