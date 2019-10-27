package bigchaindb.api.service.bigchaindb;

import bigchaindb.api.model.VideoAssetModel;
import bigchaindb.api.projection.AssetProjection;
import bigchaindb.api.repository.VideoAssetRepository;
import bigchaindb.api.service.kafka.ProduceLogRequestModelService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private VideoAssetRepository videoAssetRepository;

	private ProduceLogRequestModelService produceLogRequestModelService;

	public QueryByCameraIdAndTimestampRangeService(VideoAssetRepository videoAssetRepository, ProduceLogRequestModelService produceLogRequestModelService) {
		this.videoAssetRepository = videoAssetRepository;
		this.produceLogRequestModelService = produceLogRequestModelService;
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(long cameraId, long startDate, long endDate, long logStartDate) {
		final List<VideoAssetModel> videoAssetModels = videoAssetRepository.findByCameraIdAndStartDateAndEndDateRange(cameraId, startDate, endDate)
				.stream()
				.map(AssetProjection::getData)
				.collect(Collectors.toList());

		final Long logEndDate = Instant.now().toEpochMilli();

		produceLogRequestModelService.produceLogRequestModel(logStartDate, logEndDate);

		return videoAssetModels;
	}
}
