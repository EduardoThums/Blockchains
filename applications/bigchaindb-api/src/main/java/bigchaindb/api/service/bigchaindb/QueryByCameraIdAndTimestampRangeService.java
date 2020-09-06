package bigchaindb.api.service.bigchaindb;

import bigchaindb.api.model.VideoAssetModel;
import bigchaindb.api.projection.AssetProjection;
import bigchaindb.api.repository.VideoAssetRepository;
import bigchaindb.api.service.file.GetFilesByHashListIpfsServiceImpl;
import bigchaindb.api.service.file.GetFilesByHashListService;
import bigchaindb.api.service.file.GetFilesByHashListSwarmServiceImpl;
import bigchaindb.api.service.kafka.ProduceLogRequestModelService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private VideoAssetRepository videoAssetRepository;

	private GetFilesByHashListService getFilesByHashListService;

	public QueryByCameraIdAndTimestampRangeService(VideoAssetRepository videoAssetRepository, GetFilesByHashListIpfsServiceImpl getFilesByHashListService) {
		this.videoAssetRepository = videoAssetRepository;
		this.getFilesByHashListService = getFilesByHashListService;
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(long cameraId, long startDate, long endDate, long logStartDate) throws IOException {
		final List<VideoAssetModel> videoAssetModels = videoAssetRepository.findByCameraIdAndStartDateAndEndDateRange(cameraId, startDate, endDate)
				.stream()
				.map(AssetProjection::getData)
				.collect(Collectors.toList());

		final List<String> storageHashList = videoAssetModels
				.stream()
				.map(VideoAssetModel::getStorageHash)
				.collect(Collectors.toList());

		final List<byte[]> videoList = getFilesByHashListService.getFilesByHashList(storageHashList);

		return videoAssetModels;
	}
}
