package video.api.service.blockchain;

import org.springframework.stereotype.Service;
import video.api.client.BlockchainClient;
import video.api.model.VideoAssetModel;

import java.util.List;

/**
 * @author eduardo.thums
 */
@Service
public class QueryByCameraIdAndTimestampRangeService {

	private BlockchainClient blockchainClient;

	public QueryByCameraIdAndTimestampRangeService(BlockchainClient blockchainClient) {
		this.blockchainClient = blockchainClient;
	}

	public List<VideoAssetModel> queryByCameraIdAndTimestampRange(long cameraId, long startDate, long endDate, long logStartDate) {
		return blockchainClient.queryByCameraIdAndTimestampRange(cameraId, startDate, endDate, logStartDate);
	}
}
