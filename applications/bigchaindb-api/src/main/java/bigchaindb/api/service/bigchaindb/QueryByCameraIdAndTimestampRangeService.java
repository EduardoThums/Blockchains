package bigchaindb.api.service.bigchaindb;

import bigchaindb.api.model.VideoAssetModel;
import bigchaindb.api.projection.AssetProjection;
import bigchaindb.api.repository.VideoAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private VideoAssetRepository videoAssetRepository;

	public QueryByCameraIdAndTimestampRangeService(VideoAssetRepository videoAssetRepository) {
		this.videoAssetRepository = videoAssetRepository;
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(long cameraId, long startDate, long endDate) {
		return videoAssetRepository.findByCameraIdAndStartDateAndEndDateRange(cameraId, startDate, endDate)
				.stream()
				.map(AssetProjection::getData)
				.collect(Collectors.toList());
	}
}
