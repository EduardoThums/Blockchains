package bigchaindb.api.controller;

import bigchaindb.api.model.VideoAssetModel;
import bigchaindb.api.service.bigchaindb.QueryByCameraIdAndTimestampRangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/api/blockchain")
public class BigchaindbController {

	private QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService;

	public BigchaindbController(QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService) {
		this.queryByCameraIdAndTimestampRangeService = queryByCameraIdAndTimestampRangeService;
	}

	@GetMapping("/cameraId/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> queryByCameraIdAndTimestampRange(@PathVariable("cameraId") long cameraId,
	                                                                              @RequestParam("startDate") long startDate,
	                                                                              @RequestParam("endDate") long endDate,
	                                                                              @RequestParam("logStartDate") long logStartDate) throws IOException {

		return ResponseEntity.ok(queryByCameraIdAndTimestampRangeService.queryByCameraIdAndTimestampRange(cameraId, startDate, endDate, logStartDate));
	}
}
