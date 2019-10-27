package video.api.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import video.api.model.VideoAssetModel;
import video.api.service.blockchain.QueryByCameraIdAndTimestampRangeService;

import java.time.Instant;
import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/api/query")
public class QueryController {

	private QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService;

	public QueryController(QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService) {
		this.queryByCameraIdAndTimestampRangeService = queryByCameraIdAndTimestampRangeService;
	}

	@GetMapping("/cameraId/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> queryByCameraIdAndTimestampRange(@PathVariable("cameraId") long cameraId,
	                                                                              @RequestParam("startDate") long startDate,
	                                                                              @RequestParam("endDate") long endDate,
	                                                                              @RequestParam("logStartDate") long logStartDate) {

		final long milliLogStartDate = Instant.now().toEpochMilli();
		return ResponseEntity.ok(queryByCameraIdAndTimestampRangeService.queryByCameraIdAndTimestampRange(cameraId, startDate, endDate, milliLogStartDate));
	}
}

